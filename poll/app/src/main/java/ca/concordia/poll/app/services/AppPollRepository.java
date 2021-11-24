package ca.concordia.poll.app.services;

import ca.concordia.poll.app.dao.PollDAO;
import ca.concordia.poll.app.dao.PollDAOImpl;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.services.AbstractPollRepository;

import java.util.List;
import java.util.stream.Collectors;


public class AppPollRepository extends AbstractPollRepository {

    PollDAO pollDAOImpl;
    List<String> usedIDs;

    public AppPollRepository() {
        this.pollDAOImpl = new PollDAOImpl();
        this.usedIDs = pollDAOImpl.getAll().stream().map(Poll::getPollID).collect(Collectors.toList());
    }

    @Override
    public String save(Poll poll) {
        if (usedIDs.contains(poll.getPollID())) {
            System.out.println("Updating..");
            pollDAOImpl.update(poll);
            return poll.getPollID();
        } else {
            System.out.println("Saving..");
            String id;
            do {
                id = generateId();
            } while (usedIDs.contains(id));
            usedIDs.add(id);
            poll.setPollID(id);
            pollDAOImpl.save(poll);
            return id;
        }
    }

    @Override
    public Poll findById(String id) throws PollException {
        Poll poll = pollDAOImpl.get(id).orElse(null);
        if (poll == null) {
            throw new PollException("No poll found with that id");
        }
        return poll;
    }

    @Override
    public List<Poll> getAllPollsForAuthenticatedUser(int userID) {
        return pollDAOImpl.getAllPollsForAuthenticatedUser(userID);
    }


}
