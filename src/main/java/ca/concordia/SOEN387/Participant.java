package ca.concordia.SOEN387;

public class Participant extends User {
    Poll poll;

    public Participant(String name) {
        this.name = name;
        this.poll = Poll.getInstance();
    }

    public void vote() {}

}
