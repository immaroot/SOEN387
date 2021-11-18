package ca.concordia.poll.core;

import ca.concordia.poll.core.exceptions.AlreadyExistPollException;
import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.exceptions.WrongChoicePollException;
import ca.concordia.poll.core.exceptions.WrongStatePollException;

import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PollReadyState extends PollState {

    final static String charsForPin = "1234567890";

    public PollReadyState(Poll poll) {
        super(poll);
    }

    @Override
    public void create(String title, String question, List<Choice> choices) throws PollException {
        throw new AlreadyExistPollException("Cannot create new poll if it already exists.");
    }

    @Override
    public void update(String title, String question, List<Choice> choices) throws PollException {
        if (poll.getStatus() == PollStatus.CREATED || poll.getStatus() == PollStatus.RUNNING) {
            poll.setTitle(title);
            poll.setQuestion(question);
            poll.setChoices(choices);
            poll.getChoices().forEach(choice -> poll.getVotes().put(choice, 0));
            if (poll.getStatus() == PollStatus.RUNNING) poll.clear();
            poll.setStatus(PollStatus.CREATED);
        } else {
            throw new WrongStatePollException("A Poll not in CREATED or RUNNING state cannot be created.");
        }
    }

    @Override
    public void run() throws PollException {
        if (poll.getStatus() == PollStatus.CREATED) {
            poll.setStatus(PollStatus.RUNNING);
        } else {
            throw new WrongStatePollException("A Poll not in CREATED cannot be set to run.");
        }
    }

    @Override
    public void clear() throws PollException {
        if (poll.getStatus() == PollStatus.RUNNING) {
            poll.getVotes().clear();
            poll.getParticipantVotes().clear();
        } else if (poll.getStatus() == PollStatus.RELEASED) {
            poll.getVotes().clear();
            poll.getParticipantVotes().clear();
            poll.setStatus(PollStatus.CREATED);
        } else {
            throw new WrongStatePollException("A Poll not in RUNNING or RELEASE state cannot clear.");
        }
    }

    @Override
    public void release() throws PollException {
        if (poll.getStatus() == PollStatus.RUNNING) {
            poll.setStatus(PollStatus.RELEASED);
        } else {
            throw new WrongStatePollException("A Poll not in RUNNING state cannot be released.");
        }
    }

    @Override
    public void unrelease() throws PollException {
        if (poll.getStatus() == PollStatus.RELEASED) {
            poll.setStatus(PollStatus.RUNNING);
        } else {
            throw new WrongStatePollException("A Poll not in RELEASE state cannot be unreleased.");
        }
    }

    @Override
    public void close() {
        poll.getVotes().clear();
        poll.getParticipantVotes().clear();
        poll.changeState(new PollClosedState(poll));
    }

    @Override
    public String addVote(Choice choice) throws PollException {
        validatePollChoice(choice);
        poll.incrementVoteCount(choice);
        String pin;
        do {
            pin = generatePin();
        } while (poll.getParticipantVotes().containsKey(pin));
        poll.getParticipantVotes().put(pin, choice);
        return pin;
    }

    @Override
    public void updateVote(String pin, Choice newChoice) throws PollException {
        validatePollChoice(newChoice);
        if (poll.getParticipantVotes().containsKey(pin)) {
            Choice previousChoice = poll.getParticipantVotes().get(pin);
            poll.decrementVoteCount(previousChoice);
            poll.getParticipantVotes().remove(pin);
            poll.getParticipantVotes().put(pin, newChoice);
        } else {
            throw new PollException("The pin does not exist.");
        }
    }

    @Override
    public Hashtable<String, Integer> getResults() throws WrongStatePollException {
        if (poll.getStatus() == PollStatus.RELEASED) {
            Hashtable<String, Integer> results = new Hashtable<>();
            poll.getVotes().forEach((key, value) -> results.put(key.getTitle(), value));
            return results;
        } else {
            throw new WrongStatePollException("A Poll not in RELEASE state cannot get results.");
        }
    }

    private String generatePin() {
        return IntStream.range(0, 6).map(i -> new SecureRandom().nextInt(PollReadyState.charsForPin.length())).mapToObj(randomInt -> String.valueOf(PollReadyState.charsForPin.charAt(randomInt))).collect(Collectors.joining());
    }

    private void validatePollChoice(Choice choice) throws PollException {
        if (poll.getStatus() != PollStatus.RUNNING) {
            throw new WrongStatePollException("A Poll not in RUNNING state cannot receive a vote.");
        }
        if (!poll.getChoices().contains(choice)) {
            throw new WrongChoicePollException("Choice does not exists.");
        }
    }
}
