package ca.concordia.poll.core.users;


import ca.concordia.poll.core.Choice;
import ca.concordia.poll.core.exceptions.PollException;

public class Participant extends User {

    public Participant() {
        super();
    }

    public String vote(Choice choice) throws PollException {
       return getPoll().addVote(choice);
    }

    public void updateVote(String pin, Choice choice) throws PollException {
        getPoll().updateVote(pin, choice);
    }
}
