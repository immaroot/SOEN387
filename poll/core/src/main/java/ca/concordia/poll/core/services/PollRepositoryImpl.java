package ca.concordia.poll.core.services;

import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.exceptions.PollException;

import java.util.Hashtable;
import java.util.List;


public class PollRepositoryImpl extends AbstractPollRepository {

    private final Hashtable<String, Poll> polls;

    public PollRepositoryImpl() {
        polls = new Hashtable<>();
    }

    @Override
    public String save(Poll poll) {
        String id;
        do {
            id = generateId();
        } while (polls.containsKey(id));
        polls.put(id, poll);
        return id;
    }

    @Override
    public Poll findById(String id) throws PollException {
        if (polls.containsKey(id)) {
            return polls.get(id);
        }
        throw new PollException("No Polls exist with this ID.");
    }

    @Override
    public List<Poll> getAllPollsForAuthenticatedUser(int userID) {
        return null;
    }
}
