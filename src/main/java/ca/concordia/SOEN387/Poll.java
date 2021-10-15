package ca.concordia.SOEN387;

import java.util.ArrayList;
import java.util.List;

public class Poll {

    private static Poll poll;

    PollStatus status;
    List<Choice> choices;

    private Poll(PollStatus status) {
        this.status = status;
        this.choices = new ArrayList<>();
    }

    public static Poll getInstance() {
        if (poll == null) {
            return new Poll(PollStatus.CREATED);
        } else {
            return poll;
        }
    }

    public void changeStatus(PollStatus pollStatus) {
        this.status = pollStatus;
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }
}
