package ca.concordia.poll.core;


import ca.concordia.poll.core.exceptions.PollException;

public class Participant extends User {

    public Participant(String name) {
        super(name, Poll.getInstance());
    }

    public void vote(Choice answer) throws PollException {
       poll.addVote(this, answer);
    }
}
