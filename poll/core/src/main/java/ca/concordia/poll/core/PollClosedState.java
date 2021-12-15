package ca.concordia.poll.core;

import ca.concordia.poll.core.exceptions.ClosedPollException;
import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.users.Participant;

import java.util.Hashtable;
import java.util.List;

public class PollClosedState extends PollState{

    public PollClosedState(Poll poll) {
        super(poll);
    }

    @Override
    public void create(String name, String question, List<Choice> choices) {
        poll.setStatus(PollStatus.CREATED);
        poll.setTitle(name);
        poll.setQuestion(question);
        poll.setChoices(choices);
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
    public String addVote(Choice answer) throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }

    @Override
    public void updateVote(String pin, Choice choice) throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }

    @Override
    public Hashtable<String, Integer> getResults() throws PollException {
        throw new ClosedPollException("The poll is not yet opened..");
    }
}
