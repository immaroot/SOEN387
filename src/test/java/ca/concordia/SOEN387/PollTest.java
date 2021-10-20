package ca.concordia.SOEN387;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PollTest {

    @Test
    void testDelete() throws PollException {
        Poll poll = new Poll();
        poll.create(null, null, null);
        assert(poll.getState().getClass() == PollReadyState.class);
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