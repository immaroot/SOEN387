package ca.concordia.SOEN387.models;

import ca.concordia.SOEN387.exceptions.AlreadyExistPollException;
import ca.concordia.SOEN387.exceptions.PollException;
import ca.concordia.SOEN387.exceptions.WrongChoicePollException;
import ca.concordia.SOEN387.exceptions.WrongStatePollException;

import java.util.Hashtable;
import java.util.List;

public class PollReadyState extends PollState {

    public PollReadyState(Poll poll) {
        super(poll);
    }

    @Override
    public void create(String name, String question, List<Choice> choices) throws PollException {
        throw new AlreadyExistPollException("Cannot create new poll if it already exists.");
    }

    @Override
    public void update(String name, String question, List<Choice> choices) throws PollException {
        if (poll.getStatus() == PollStatus.CREATED || poll.getStatus() == PollStatus.RUNNING) {
            poll.setName(name);
            poll.setQuestion(question);
            poll.setChoices(choices);
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
    public void addVote(Participant participant, Choice answer) throws PollException {
        if (poll.getStatus() == PollStatus.RUNNING) {
            if (poll.getChoices().contains(answer)) {
                if (poll.getParticipantVotes().containsKey(participant)) {
                    Choice option = poll.getParticipantVotes().get(participant);
                    poll.removeVote(participant, option);
                }
                int numVotes = poll.getVotes().get(answer) == null ? 0 : poll.getVotes().get(answer);
                poll.getVotes().put(answer, ++numVotes);
                poll.getParticipantVotes().put(participant, answer);
            } else {
                throw new WrongChoicePollException("Choice does not exists.");
            }
        } else {
            throw new WrongStatePollException("A Poll not in RUNNING state cannot receive a vote.");
        }
    }

    @Override
    public Hashtable<String, Integer> getResults() throws WrongStatePollException {
        if (poll.getStatus() == PollStatus.RELEASED) {
            Hashtable<String, Integer> results = new Hashtable<>();
            poll.getVotes().forEach((key, value) -> results.put(key.toString(), value));
            return results;
        } else {
            throw new WrongStatePollException("A Poll not in RELEASE state cannot get results.");
        }
    }


}
