package ca.concordia.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Poll {

    private String title;
    private String question;
    private PollStatus status;
    private List<Choice> choices;

    public Poll() {
        this.title = "";
        this.question = "";
        this.status = PollStatus.CREATED;
        this.choices = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void update(String name, String question, List<Choice> choices) throws PollException {
        this.title = name;
        this.question = question;
        this.choices = choices;
    }

    public void clear() {
        this.title = "";
        this.question = "";
        this.choices = new ArrayList<>();
    }

    protected void setQuestion(String question) {
        this.question = question;
    }

    public PollStatus getStatus() {
        return status;
    }

    public void setStatus(PollStatus status) {
        this.status = status;
    }

    protected void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }
}
