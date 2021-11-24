package ca.concordia.poll.app.dao;

import ca.concordia.poll.core.Choice;
import ca.concordia.poll.core.Poll;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

public interface PollDAO {

    public Optional<Poll> get(String pollID);

    public List<Poll> getAll();

    public Poll save(Poll poll);

    public Poll update(Poll poll);

    public List<Choice> getChoices(String pollID);

    public Hashtable<Choice, Integer> getVotes(String pollID);

    public Hashtable<String, Choice> getParticipantVotes(String pollID);

    public List<Poll> getAllPollsForAuthenticatedUser(int userID);

}
