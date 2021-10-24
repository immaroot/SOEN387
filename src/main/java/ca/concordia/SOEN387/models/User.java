package ca.concordia.SOEN387.models;

import ca.concordia.SOEN387.exceptions.PollException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Date;
import java.util.Hashtable;
import java.util.Objects;

public class User {

    String name;
    Poll poll;

    public User(String name, Poll poll) {
        this.name = name;
        this.poll = poll;
    }

    public Hashtable<String, Integer> getPollResults() throws PollException {
        return poll.getResults();
    }

    public void downloadPollDetails(PrintWriter printWriter, String filename) throws IOException, PollException {
        getPollResults().forEach((s, integer) -> {
            printWriter.write(s + ": \t\t\t" + integer.toString() + "\n");
        });
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
