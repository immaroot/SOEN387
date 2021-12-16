package ca.concordia.poll.app.dao;

import ca.concordia.poll.core.Choice;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.Vote;

import java.util.List;
import java.util.Optional;

public interface PollDAO {

    Optional<Poll> get(String pollID);

    List<Poll> getAll();

    Poll save(Poll poll);

    Poll update(Poll poll);

    List<Choice> getChoices(String pollID);

    List<Vote>  getVotes(String pollID);

    List<Poll> getAllPollsForAuthenticatedUser(int userID);

}
