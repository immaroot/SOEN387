package ca.concordia.SOEN387.models;

import ca.concordia.SOEN387.exceptions.PollException;

public class Participant extends User {

    public Participant(String name) {
        super(name, Poll.getInstance());
    }

    public void vote(Choice answer) throws PollException {
       poll.addVote(this, answer);
    }
}
