package ca.concordia.poll.core;

import ca.concordia.poll.core.exceptions.PollException;

import java.util.List;

public class PollManager extends UserBase {

    private int userID;
    private String fullName;
    private String email;
    private String password;

    public PollManager() {
        super();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void createPoll(String name, String question, List<Choice> choices) throws PollException {
        getPoll().create(name, question, choices);
    }

    public void updatePoll(String name, String question, List<Choice> choices) throws PollException {
        getPoll().update(name, question, choices);
    }

    public void clearPoll() throws PollException {
        getPoll().clear();
    }

    public void closePoll() throws PollException {
        getPoll().close();
    }

    public void runPoll() throws PollException {
        getPoll().run();
    }

    public void releasePoll() throws PollException {
        getPoll().release();
    }

    public void unreleasePoll() throws PollException {
        getPoll().unrelease();
    }

    @Override
    public String toString() {
        return "PollManager{" +
                "userID=" + userID +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
