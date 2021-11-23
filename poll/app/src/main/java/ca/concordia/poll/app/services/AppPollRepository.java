package ca.concordia.poll.app.services;

import ca.concordia.poll.app.dao.PollDAO;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.services.AbstractPollRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class AppPollRepository extends AbstractPollRepository {

    PollDAO pollDAO;
    List<String> usedIDs;

    public AppPollRepository() {
        this.pollDAO = new PollDAO();
        this.usedIDs = pollDAO.getAll().stream().map(Poll::getPollID).collect(Collectors.toList());
    }

    @Override
    public String save(Poll poll) {
        if (usedIDs.contains(poll.getPollID())) {
            pollDAO.update(poll, null);
            return poll.getPollID();
        } else {
            String id;
            do {
                id = generateId();
            } while (usedIDs.contains(id));
            usedIDs.add(id);
            poll.setPollID(id);
            pollDAO.save(poll);
            return id;
        }
    }

    @Override
    public Poll findById(String id) throws PollException {
        Poll returnedPoll = pollDAO.getAll().stream().filter(poll -> poll.getPollID().equals(id)).findFirst().orElse(null);
        if (returnedPoll == null) {
            throw new PollException("No poll found with that id");
        }
        return returnedPoll;
    }

}
