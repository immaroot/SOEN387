package ca.concordia.poll.app.entities;

import ca.concordia.poll.core.Vote;

import javax.persistence.*;

@Entity
@Table(name = "votes")
@IdClass(VoteID.class)
public class VoteEntity extends Vote {

    @Id
    private String pollID;

    @Id
    private String pin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "choice_id", referencedColumnName = "id"),
            @JoinColumn(name = "poll_id", referencedColumnName = "poll_id")
    })
    private ChoiceEntity choice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    private PollEntity poll;

    public VoteEntity() {
    }


}
