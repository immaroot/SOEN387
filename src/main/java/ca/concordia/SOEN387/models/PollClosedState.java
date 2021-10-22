package ca.concordia.SOEN387.models;

import ca.concordia.SOEN387.exceptions.ClosedPollException;
import ca.concordia.SOEN387.exceptions.PollException;

import java.util.Hashtable;
import java.util.List;

public class PollClosedState extends PollState{

    public PollClosedState(Poll poll) {
        super(poll);
    }

    @Override
    public void create(String name, String question, List<Choice> choices) {
        poll.setStatus(PollStatus.CREATED);
        poll.setName(name);
        poll.setQuestion(question);
        poll.setChoices(choices);
        poll.getChoices().forEach(choice -> poll.getVotes().put(choice, 0));
        poll.changeState(new PollReadyState(poll));
    }

    @Override
    public void update(String name, String question, List<Choice> choices) throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }

    @Override
    public void run() throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }

    @Override
    public void clear() throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }

    @Override
    public void release() throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }

    @Override
    public void unrelease() throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }

    @Override
    public void close() throws ClosedPollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }

    @Override
    public void addVote(Participant participant, Choice answer) throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }

    @Override
    public Hashtable<String, Integer> getResults() throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }
}
