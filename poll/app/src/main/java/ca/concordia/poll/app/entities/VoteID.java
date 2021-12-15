package ca.concordia.poll.app.entities;

import java.io.Serializable;
import java.util.Objects;

public class VoteID implements Serializable {

    private String pollID;

    private String pin;

    public VoteID() {
    }

    public VoteID(String pollID, String pin) {
        this.pollID = pollID;
        this.pin    = pin;
    }

    public String getPollID() {
        return pollID;
    }

    public void setPollID(String pollID) {
        this.pollID = pollID;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteID)) return false;
        VoteID voteID = (VoteID) o;
        return pollID.equals(voteID.pollID) && pin.equals(voteID.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pollID, pin);
    }

}
