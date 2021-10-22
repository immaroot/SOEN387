package ca.concordia.SOEN387.models;

import ca.concordia.SOEN387.exceptions.PollException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        getPollResults().forEach((s, integer) -> {
            try {
                fw.write(s + ":" + integer.toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.flush();
        fw.close();
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
