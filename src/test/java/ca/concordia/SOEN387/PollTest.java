package ca.concordia.SOEN387;

import ca.concordia.SOEN387.exceptions.ClosedPollException;
import ca.concordia.SOEN387.exceptions.PollException;
import ca.concordia.SOEN387.exceptions.WrongStatePollException;
import ca.concordia.SOEN387.models.Poll;
import ca.concordia.SOEN387.models.PollManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PollTest {

    @Test
    void testDelete() throws PollException {
        Poll poll = Poll.getInstance();
        poll.create(null, null, null);
        assert(poll.isOpen());
    }

    @Test
    void testWrongStatePollException() throws PollException {
        PollManager manager = new PollManager("Test");
        manager.createPoll(null, null, null);
        assertThrows(WrongStatePollException.class, manager::releasePoll);
    }

    @Test
    void testClosedPollException() {
        PollManager manager = new PollManager("Test");
        assertThrows(ClosedPollException.class, manager::releasePoll);
    }
}