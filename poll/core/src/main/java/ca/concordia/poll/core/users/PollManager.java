package ca.concordia.poll.core.users;

import ca.concordia.poll.core.Choice;
import ca.concordia.poll.core.exceptions.PollException;

import java.util.List;

public class PollManager extends AuthenticatedUser {

    public PollManager() {
        super();
    }

    public void createPoll(String name, String question, List<Choice> choices) throws PollException {
        getPoll().create(name, question, choices);
    }

    public void updatePoll(String name, String question, List<Choice> choices) throws PollException {
        getPoll().update(name, question, choices);
    }

    public void clearPoll() throws PollException {
        getPoll().clear();
    }

    public void closePoll() throws PollException {
        getPoll().close();
    }

    public void runPoll() throws PollException {
        getPoll().run();
    }

    public void releasePoll() throws PollException {
        getPoll().release();
    }

    public void unreleasePoll() throws PollException {
        getPoll().unrelease();
    }
}
