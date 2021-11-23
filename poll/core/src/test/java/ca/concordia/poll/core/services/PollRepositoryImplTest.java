package ca.concordia.poll.core.services;

import ca.concordia.poll.core.Choice;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.users.Participant;
import ca.concordia.poll.core.users.PollManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;


class PollRepositoryImplTest {

    List<Choice> choices;
    String pollID;
    PollRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        PollManager manager = new PollManager();
        Participant participant = new Participant();
        Choice choice1 = new Choice("A test option1");
        Choice choice2 = new Choice("A test option2", "description");
        choices = new LinkedList<>();
        choices.add(choice1);
        choices.add(choice2);
        manager.setPoll(new Poll());
        try {
            manager.createPoll("name", "question", choices);
        } catch (PollException e) {
            e.printStackTrace();
        }
        repository = new PollRepositoryImpl();
        pollID = repository.save(manager.getPoll());
    }

    @Test
    void save() throws PollException {
        Poll poll = new Poll();
        try {
            poll.create("new poll", "new question", choices);
        } catch (PollException e) {
            e.printStackTrace();
        }

        String newPollID = repository.save(poll);
        System.out.println("pollID: " + pollID);
        System.out.println("newPollID: " + newPollID);
        Assertions.assertNotEquals(repository.findById(pollID), repository.findById(newPollID));
    }

    @Test
    void findById() throws PollException {
        Poll testPoll = repository.findById(pollID);
        System.out.println("pollID: " + pollID);
        System.out.println("testPoll.getTitle(): " + testPoll.getTitle());
        Assertions.assertNotNull(testPoll);
        Assertions.assertEquals(testPoll.getTitle(), "name");
    }
}