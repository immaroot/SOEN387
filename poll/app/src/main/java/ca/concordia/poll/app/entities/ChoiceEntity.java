package ca.concordia.poll.app.entities;

import ca.concordia.poll.core.Choice;

import javax.persistence.*;

@Entity
@Table(name = "choices")
@IdClass(ChoiceID.class)
public class ChoiceEntity extends Choice {

    @Id
    private int id;

    @Id
    private String pollID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    private PollEntity poll;


    public ChoiceEntity() {
    }
}
