package ca.concordia.poll.app.entities;

import java.io.Serializable;
import java.util.Objects;

public class ChoiceID implements Serializable {

    private int id;

    private String pollID;

    public ChoiceID() {
    }

    public ChoiceID(int id, String pollID) {
        this.id     = id;
        this.pollID = pollID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPollID() {
        return pollID;
    }

    public void setPollID(String pollID) {
        this.pollID = pollID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChoiceID)) return false;
        ChoiceID choiceID = (ChoiceID) o;
        return getId() == choiceID.getId() && getPollID().equals(choiceID.getPollID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPollID());
    }
}
