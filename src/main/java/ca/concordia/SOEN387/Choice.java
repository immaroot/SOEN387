package ca.concordia.SOEN387;

import java.util.Objects;

public class Choice {

    String title;
    String description;

    public Choice(String title) {
        this.title = title;
    }

    public Choice(String title, String description) {
        this.title       = title;
        this.description = description;
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
