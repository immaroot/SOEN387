package ca.concordia.SOEN387;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class ParticipantTest {

    @Test
    void testVoteOnNullPoll() {
        PollManager manager = new PollManager("Test");
        Participant participant = new Participant("TestParticipant", manager.getPoll());
        Choice choice1 = new Choice("A test option1");
        Choice choice2 = new Choice("A test option2", "description");
        List<Choice> choices = new LinkedList<>();
        choices.add(choice1);
        choices.add(choice2);
        try {
            manager.createPoll("TestPoll", "Test?", choices);
            manager.runPoll();
        } catch (PollException e) {
            e.printStackTrace();
        }
        try {
            participant.vote(choice1);
        } catch (PollException e) {
            e.printStackTrace();
        }
    }
}