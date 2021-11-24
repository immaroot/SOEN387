package ca.concordia.poll.core.services;

import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.exceptions.PollException;

import java.util.List;

public interface PollRepository {

    String save(Poll poll);

    Poll findById(String id) throws PollException;

    List<Poll> getAllPollsForAuthenticatedUser(int userID);
}
