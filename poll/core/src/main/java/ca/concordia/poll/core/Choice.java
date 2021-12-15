package ca.concordia.poll.core;

import java.io.Serializable;
import java.util.Objects;

public class Choice implements Serializable {

    String title, description;
    int choiceID;

    public Choice() {
    }

    public Choice(String title) {
        this.title = title;
    }

    public Choice(String title, String description) {
        this.title       = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(int choiceID) {
        this.choiceID = choiceID;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return title.equals(choice.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
