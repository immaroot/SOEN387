package ca.concordia.poll.core;

import ca.concordia.poll.core.exceptions.PollException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Poll implements Serializable {

    private String pollID, title, question;
    private int createdBy;
    private PollStatus status;
    private List<Choice> choices;
    private List<Vote> votes;
    private PollState state;

    public Poll() {
        this.state = new PollClosedState(this);
        this.status = PollStatus.CREATED;
        this.choices = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public void create(String title, String question, List<Choice> choices) throws PollException {
        state.create(title, question, choices);
    }

    public void update(String title, String question, List<Choice> choices) throws PollException {
        state.update(title, question, choices);
    }

    public void run() throws PollException {
        state.run();
    }

    public void clear() throws PollException {
        state.clear();
    }

    public void release() throws PollException {
        state.release();
    }

    public void unrelease() throws PollException {
        state.unrelease();
    }

    public void close() throws PollException {
        state.close();
    }

    public String addVote(Choice choice) throws PollException {
        return state.addVote(choice);
    }

    public void updateVote(String pin, Choice choice) throws PollException {
        state.updateVote(pin, choice);
    }

    public Hashtable<String, Integer> getResults() throws PollException {
        return state.getResults();
    }

    public String getPollID() {
        return pollID;
    }

    public void setPollID(String pollID) {
        this.pollID = pollID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    protected void changeState(PollState state) {
        this.state = state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }

    public PollStatus getStatus() {
        return status;
    }

    public void setStatus(PollStatus status) {
        this.status = status;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public PollState getState() {
        return state;
    }

    public void setState(PollState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "pollID='" + pollID + '\'' +
                ", title='" + title + '\'' +
                ", question='" + question + '\'' +
                ", createdBy=" + createdBy +
                ", status=" + status +
                ", choices=" + choices +
                ", votes=" + votes +
                ", state=" + state +
                '}';
    }

    public boolean isOpen() {
        return state.getClass() == PollReadyState.class;
    }
}
