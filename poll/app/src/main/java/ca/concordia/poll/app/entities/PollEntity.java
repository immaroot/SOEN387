package ca.concordia.poll.app.entities;

import ca.concordia.poll.core.Poll;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "polls")
public class PollEntity extends Poll {

    public void setPollID(String pollID) {
        super.setPollID(pollID);
    }

    @Id
    public String getPollID() {
        return super.getPollID();
    }

    @Column(name = "user_id")
    int createdBy;

    @Column(name = "title")
    String title;

    @Column(name = "question")
    String question;

    @Column(name = "status")
    int status;

    @Column(name = "state")
    int state;


}
