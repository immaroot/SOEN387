package ca.concordia.poll.core.services;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract public class AbstractPollRepository implements PollRepository {

    private final static String charsForPollID = "ABCDEFGHJKMNPQRSTUVWXYZ1234567890";

    public String generateId() {
        return IntStream.range(0, 10).map(i -> new SecureRandom().nextInt(charsForPollID.length())).mapToObj(randomInt -> String.valueOf(charsForPollID.charAt(randomInt))).collect(Collectors.joining());
    }
}
