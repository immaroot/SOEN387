package ca.concordia.poll.core.services;

import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.exceptions.PollException;

import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PollRepositoryImpl implements PollRepository {

    private final static String charsForPollID = "ABCDEFGHJKMNPQRSTUVWXYZ1234567890";
    private final Hashtable<String, Poll> polls;

    public PollRepositoryImpl() {
        polls = new Hashtable<>();
    }

    @Override
    public String save(Poll poll) {
        String id;
        do {
            id = generateId();
        } while (polls.containsKey(id));
        polls.put(id, poll);
        return id;
    }

    @Override
    public Poll findById(String id) throws PollException {
        if (polls.containsKey(id)) {
            return polls.get(id);
        }
        throw new PollException("No Polls exist with this ID.");
    }

    private String generateId() {
        return IntStream.range(0, 10).map(i -> new SecureRandom().nextInt(PollRepositoryImpl.charsForPollID.length())).mapToObj(randomInt -> String.valueOf(PollRepositoryImpl.charsForPollID.charAt(randomInt))).collect(Collectors.joining());
    }
}
