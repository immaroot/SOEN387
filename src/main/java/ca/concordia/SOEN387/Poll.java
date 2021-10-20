package ca.concordia.SOEN387;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Poll {

    private String name, question;
    private PollStatus status;
    private List<Choice> choices;
    private final Hashtable<Choice, Integer> votes;
    private final Hashtable<Participant, Choice> participantVotes;
    private PollState state;

    public Poll() {
        this.state = new PollClosedState(this);
        this.status = PollStatus.CREATED;
        this.choices = new ArrayList<>();
        this.votes = new Hashtable<>();
        this.participantVotes = new Hashtable<>();
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
        state.clear();
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

    public Hashtable<String, Integer> getResults() {
        Hashtable<String, Integer> results = new Hashtable<>();
        votes.forEach((key, value) -> results.put(key.toString(), value));
        return results;
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

    protected PollStatus getStatus() {
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

    public Object getState() {
        return state;
    }
}
