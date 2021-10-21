package ca.concordia.SOEN387.models;

import ca.concordia.SOEN387.exceptions.ClosedPollException;
import ca.concordia.SOEN387.exceptions.PollException;

import java.io.IOException;
import java.io.PrintWriter;
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

    abstract public void addVote(Participant participant, Choice answer) throws PollException;

    abstract public Hashtable<String, Integer> getResults() throws PollException;

    abstract public void downloadPollDetails(PrintWriter printWriter, String filename) throws IOException, ClosedPollException;
}
