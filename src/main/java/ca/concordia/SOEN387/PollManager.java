package ca.concordia.SOEN387;

import java.util.List;

public class PollManager extends User {

    public PollManager(String name) {
        super(name, new Poll());
    }

    public void createPoll(String name, String question, List<Choice> choices) throws PollException {
        poll.create(name, question, choices);
    }

    public void updatePoll(String name, String question, List<Choice> choices) throws PollException {
        poll.update(name, question, choices);
    }

    public void clearPoll() throws PollException {
        poll.clear();
    }

    public void closePoll() throws PollException {
        poll.close();
    }

    public void runPoll() throws PollException {
        poll.run();
    }

    public void releasePoll() throws PollException {
        poll.release();
    }

    public void unreleasePoll() throws PollException {
        poll.unrelease();
    }

    public Poll getPoll() {
        return poll;
    }
}
