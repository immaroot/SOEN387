package ca.concordia.SOEN387;

import java.util.List;

public class PollManager extends User {

    Poll poll;

    public PollManager(String name) {
        this.name = name;
        this.poll = Poll.getInstance();
    }

    public void createPoll(String name, String question, List<String> choices) {
    }

    public void updatePoll(String name, String question, List<String> choices) {
    }

    public void clearPoll() {
    }

    public void closePoll() {
    }

    public void runPoll() {
    }

    public void releasePoll() {}

    public void unreleasePoll() {}
}
