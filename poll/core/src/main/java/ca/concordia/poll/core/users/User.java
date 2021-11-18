package ca.concordia.poll.core.users;

import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.exceptions.PollException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Hashtable;

public class User implements Serializable {

    private Poll poll;

    public User() {
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
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
}
