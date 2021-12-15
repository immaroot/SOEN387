package ca.concordia.poll.core;

import java.io.Serializable;
import java.util.Objects;

public class Vote implements Serializable {

    private String pin;
    private Choice choice;

    public Vote() {
    }
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "pin='" + pin + '\'' +
                ", choice=" + choice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return getPin().equals(vote.getPin()) && getChoice().equals(vote.getChoice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPin(), getChoice());
    }
}
