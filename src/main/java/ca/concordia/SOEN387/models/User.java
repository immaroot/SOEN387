package ca.concordia.SOEN387.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

public class User {

    String name;
    Poll poll;

    public User(String name, Poll poll) {
        this.name = name;
        this.poll = poll;
    }

    public Hashtable<String, Integer> getPollResults() {
        return poll.getResults();
    }

    public void downloadPollDetails(PrintWriter printWriter, String filename) throws IOException {
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        getPollResults().forEach(((s, integer) -> {
            try {
                fw.write(s + integer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        fw.flush();
        fw.close();
    }
}
