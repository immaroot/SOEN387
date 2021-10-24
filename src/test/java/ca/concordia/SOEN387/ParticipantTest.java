package ca.concordia.SOEN387;

import ca.concordia.SOEN387.exceptions.PollException;
import ca.concordia.SOEN387.models.Choice;
import ca.concordia.SOEN387.models.Participant;
import ca.concordia.SOEN387.models.PollManager;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class ParticipantTest {

    @Test
    void testVoteOnNullPoll() {
        PollManager manager = new PollManager("Test");
        Participant participant = new Participant("TestParticipant");
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