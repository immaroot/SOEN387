package ca.concordia.SOEN387.models;

import ca.concordia.SOEN387.exceptions.PollException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Poll implements Serializable {

    private static Poll poll;
    private String name, question;
    private PollStatus status;
    private List<Choice> choices;
    private final Hashtable<Choice, Integer> votes;
    private final Hashtable<Participant, Choice> participantVotes;
    private PollState state;

    private Poll() {
        this.state = new PollClosedState(this);
        this.status = PollStatus.CREATED;
        this.choices = new ArrayList<>();
        this.votes = new Hashtable<>();
        this.participantVotes = new Hashtable<>();
        poll = this;
    }

    public static Poll getInstance() {
        if (poll == null) {
            return new Poll();
        } else {
            return poll;
        }
    }

    public void create(String name, String question, List<Choice> choices) throws PollException {
        state.create(name, question, choices);
    }

    public void update(String name, String question, List<Choice> choices) throws PollException {
        state.update(name, question, choices);
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

    public void addVote(Participant participant, Choice answer) throws PollException {
        state.addVote(participant, answer);
    }

    public void removeVote(Participant participant, Choice choice) {
        int currentNumberOfVotes = votes.get(choice);
        votes.put(choice, --currentNumberOfVotes);
        participantVotes.remove(participant);
    }

    public Hashtable<String, Integer> getResults() throws PollException {
        return state.getResults();
    }

    public void changeState(PollState state) {
        this.state = state;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setQuestion(String question) {
        this.question = question;
    }

    public String getName() {
        return name;
    }

    public String getQuestion() {
        return question;
    }

    public PollStatus getStatus() {
        return status;
    }

    protected void setStatus(PollStatus status) {
        this.status = status;
    }

    protected void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    protected Hashtable<Choice, Integer> getVotes() {
        return votes;
    }

    protected Hashtable<Participant, Choice> getParticipantVotes() {
        return participantVotes;
    }

    protected Object getState() {
        return state;
    }

    public boolean isOpen() {
        return state.getClass() == PollReadyState.class;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", status=" + status +
                ", choices=" + choices +
                ", votes=" + votes +
                ", participantVotes=" + participantVotes +
                ", state=" + state +
                '}';
    }
}
