package ca.concordia.service;

import ca.concordia.core.*;
import ca.concordia.model.Vote;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class PollService {
    private static PollService ourInstance = new PollService();

    public static PollService getInstance() {
        return ourInstance;
    }

    private Poll poll;
    private Date dateReleased;
    private Map<String, Vote> voteMap = new HashMap<>(10);

    private PollService() {
    }

    public void vote(String sessionId, int choice) throws PollException {
        if (poll == null || poll.getStatus() != PollStatus.RUNNING) {
            throw new WrongStatePollException("Poll is not running.");
        }
        Vote vote = new Vote(choice, new Date());
        voteMap.put(sessionId, vote);
    }

    public void createPoll(String name, String question, List<Choice> choices) throws PollException {
        if (poll != null) throw new AlreadyExistPoll("");
        poll = new Poll();
        poll.update(name, question, choices);
    }

    public void updatePoll(String name, String question, List<Choice> choices) throws PollException {
        if (poll == null) throw new WrongStatePollException("Not created");
        if (poll.getStatus() != PollStatus.RUNNING && poll.getStatus() != PollStatus.CREATED)
            throw new WrongStatePollException("Neither running nor created");
        poll.update(name, question, choices);
        voteMap.clear();
    }

    public void clearPoll() throws PollException {
        if (poll == null) throw new WrongStatePollException("Not created");
        if (poll.getStatus() != PollStatus.RUNNING && poll.getStatus() != PollStatus.RELEASED)
            throw new WrongStatePollException("Neither running nor released");
        if (poll.getStatus() == PollStatus.RELEASED) {
            poll.setStatus(PollStatus.CREATED);
        }
        poll.clear();
        voteMap.clear();
    }

    public void closePoll() throws PollException {
        if (poll.getStatus() != PollStatus.RELEASED)
            throw new WrongStatePollException("Not released");
        poll = null;
    }

    public void runPoll() throws PollException {
        if (poll.getStatus() != PollStatus.CREATED)
            throw new WrongStatePollException("Not created");
        poll.setStatus(PollStatus.RUNNING);
    }

    public void releasePoll() throws PollException {
        if (poll.getStatus() != PollStatus.RUNNING)
            throw new WrongStatePollException("Not running");
        poll.setStatus(PollStatus.RELEASED);
        dateReleased = new Date();
    }

    public void unreleasePoll() throws PollException {
        if (poll.getStatus() != PollStatus.RELEASED)
            throw new WrongStatePollException("Not released");
        poll.setStatus(PollStatus.RUNNING);
    }

    public Poll getPoll() {
        return poll;
    }

    public Date getDateReleased() {
        return dateReleased;
    }

    public Map<String, Integer> getPollResults() {
        Map<String, Integer> map = new Hashtable<>();
        if (poll == null) return map;
        for(Vote vote : voteMap.values()) {
            Choice choice = poll.getChoices().get(vote.getChoice());
            int count = map.getOrDefault(choice.getTitle(), 0);
            map.put(choice.getTitle(), ++count);
        }
        return map;
    }

    public void downloadPollDetails(HttpServletResponse response, String name, String format) throws IOException {
        if (poll == null || poll.getStatus() != PollStatus.RELEASED) {
            response.getWriter().write("Not released yet.");
            return;
        }
        if (!poll.getTitle().equals(name)) {
            response.getWriter().write("Name parameter doesn't match with poll name.");
            return;
        }
        if (!"text".equals(format)) {
            response.getWriter().write("Format should be 'text'.");
            return;
        }

        String fileName = name + "-" + dateReleased.getTime() + ".txt";
        StringBuilder content = new StringBuilder("Vote results goes here.\r\n");
        Map<String, Integer> map = getPollResults();
        for(Choice choice : poll.getChoices()) {
            int count = map.getOrDefault(choice.getTitle(), 0);
            content.append(choice.getTitle());
            content.append(" : ");
            content.append(count);
            content.append("\r\n");
        }

        response.setHeader("Expires", "-1");
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
        response.getOutputStream().write(content.toString().getBytes());
        response.getOutputStream().flush();
    }
}
