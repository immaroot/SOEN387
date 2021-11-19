package ca.concordia.poll.core;

import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.users.Participant;

import java.util.Hashtable;
import java.util.List;

public abstract class PollState {

    Poll poll;

    public PollState(Poll poll) {
        this.poll = poll;
    }

    abstract public void create(String name, String question, List<Choice> choices) throws PollException;

    abstract public void update(String name, String question, List<Choice> choices) throws PollException;

    abstract public void run() throws PollException;

    abstract public void clear() throws PollException;

    public abstract void release() throws PollException;

    abstract public void unrelease() throws PollException;

    abstract public void close() throws PollException;

    abstract public String addVote(Choice choice) throws PollException;

    abstract public void updateVote(String pin, Choice choice) throws PollException;

    abstract public Hashtable<String, Integer> getResults() throws PollException;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
