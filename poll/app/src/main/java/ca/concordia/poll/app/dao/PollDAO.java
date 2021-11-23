package ca.concordia.poll.app.dao;

import ca.concordia.poll.app.db.DBConnection;
import ca.concordia.poll.core.*;


import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class PollDAO implements DAO<Poll> {


    @Override
    public Optional<Poll> get(int id) {
        Connection connection = DBConnection.getConnection();

        try {
            String query = "SELECT id, user_id, title, question FROM polls WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(extractPollFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    private Poll extractPollFromResultSet(ResultSet result) throws SQLException {
        String id = result.getString("id");
        Poll poll = new Poll();
        poll.setPollID(result.getString("id"));
        poll.setTitle(result.getString("title"));
        poll.setQuestion(result.getString("question"));
        poll.setStatus(PollStatus.valueOf(result.getInt("status")));
        poll.setState(result.getInt("state") == 0 ? new PollClosedState(poll) : new PollReadyState(poll));
        poll.setChoices(getChoices(id));
        poll.setVotes(getVotes(id, poll.getChoices()));
        poll.setParticipantVotes(getParticipantVotes(id, poll.getChoices()));

        return poll;
    }

    private List<Choice> getChoices(String pollId) {
        Connection connection = DBConnection.getConnection();
        List<Choice> choices = new ArrayList<>();
        try {
            String query = "SELECT id, title, description FROM choices WHERE poll_id=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pollId);
            ResultSet choicesResultSet = ps.executeQuery();

            while (choicesResultSet.next()) {
                choices.add(new Choice(choicesResultSet.getString("title"), choicesResultSet.getString("description")));
            }
            return choices;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Hashtable<Choice, Integer> getVotes(String pollID, List<Choice> choices) {
        Hashtable<Choice, Integer> votes = new Hashtable<>();
        Connection connection = DBConnection.getConnection();
        try {
            int bound = choices.size();
            for (int i = 0; i < bound; i++) {
                String query = "SELECT count(*) FROM votes WHERE poll_id=? AND choice_id=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, pollID);
                ps.setInt(2, i);
                ResultSet votesResultSet = ps.executeQuery();
                if (votesResultSet.next()) {
                    Integer count = votesResultSet.getInt("count");
                    votes.put(choices.get(i), count);
                }
            }
            return votes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Hashtable<String, Choice> getParticipantVotes(String pollID, List<Choice> choices) {
        Hashtable<String, Choice> participantVotes = new Hashtable<>();
        Connection connection = DBConnection.getConnection();
        try {
            int bound = choices.size();
            for (int i = 0; i < bound; i++) {
                String query = "SELECT pin FROM votes WHERE poll_id=? AND choice_id=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, pollID);
                ps.setInt(2, i);
                ResultSet participantVotesResultSet = ps.executeQuery();
                while (participantVotesResultSet.next()) {
                    participantVotes.put(participantVotesResultSet.getString("pin"), choices.get(i));
                }
            }
            return participantVotes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Poll> getAll() {
        Connection connection = DBConnection.getConnection();

        try {
            String query = "SELECT id, user_id, title, question, status, state FROM polls";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            List<Poll> polls = new ArrayList<>();

            while (rs.next()) {
                polls.add(extractPollFromResultSet(rs));
            }

            return polls;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Poll save(Poll poll) {

        Connection connection = DBConnection.getConnection();

        try {
            String query = "INSERT INTO polls (id, user_id, title, question, status, state) VALUES (?,?,?,?,?,?)";

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, poll.getPollID());
            ps.setInt(2, poll.getCreatedBy());
            ps.setString(3, poll.getTitle());
            ps.setString(4, poll.getQuestion());
            ps.setInt(5, poll.getStatus().getIndex());
            ps.setInt(6, poll.getState().getClass().equals(PollClosedState.class) ? 0 : 1);

            if (ps.executeUpdate() == 1) {
                saveAllChoices(poll);
                saveAllVotes(poll);

                return poll;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private void saveAllVotes(Poll poll) throws SQLException {
        for (Map.Entry<String, Choice> entry : poll.getParticipantVotes().entrySet()) {
            String pin = entry.getKey();
            Choice choice = entry.getValue();
            saveVote(poll.getPollID(), pin, poll.getChoices().indexOf(choice));
        }
    }

    private void saveAllChoices(Poll poll) throws SQLException {
        int bound = poll.getChoices().size();
        for (int i = 0; i < bound; i++) {
            saveChoice(poll.getChoices().get(i), i, poll.getPollID());
        }
    }

    @Override
    public Poll update(Poll poll, String[] params) {
        Connection connection = DBConnection.getConnection();

        try {
            String query = "UPDATE polls SET (user_id, title, question, status, state)=(?,?,?,?,?) WHERE id=?";

            PreparedStatement ps = connection.prepareStatement(query);


            ps.setInt(1, poll.getCreatedBy());
            ps.setString(2, poll.getTitle());
            ps.setString(3, poll.getQuestion());
            ps.setInt(4, poll.getStatus().getIndex());
            ps.setInt(5, poll.getState().getClass().equals(PollClosedState.class) ? 0 : 1);
            ps.setString(6, poll.getPollID());

            if (ps.executeUpdate() == 1) {
                updateAllChoices(poll);
                updateAllVotes(poll);

                return poll;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Poll delete(Poll poll) {
        return null;
    }

    private void saveChoice(Choice choice, int i, String pollID) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String query = "INSERT INTO choices (id, poll_id, title, description) VALUES (?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, i);
        ps.setString(2, pollID);
        ps.setString(3, choice.getTitle());
        ps.setString(4, choice.getDescription());

        ps.executeUpdate();
    }

    private void saveVote(String pollID, String pin, int choiceID) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String query = "INSERT INTO votes (poll_id, pin, choice_id) VALUES (?,?,?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, pollID);
        ps.setString(2, pin);
        ps.setInt(3, choiceID);

        ps.executeUpdate();
    }

    private void updateChoice(Choice choice, int i, String pollID) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String getChoicesIDQuery = "SELECT MAX(id) FROM choices WHERE poll_id=?";
        PreparedStatement ps = connection.prepareStatement(getChoicesIDQuery);
        ps.setString(1, pollID);
        ResultSet rs = ps.executeQuery();

        String query;
        if (rs.next()) {
            if (i <= rs.getInt(1)) {
                query = "UPDATE choices SET id=?, poll_id=?, title=?, description=? WHERE id=? AND poll_id=?";
                ps    = connection.prepareStatement(query);
                ps.setInt(1, i);
                ps.setString(2, pollID);
                ps.setString(3, choice.getTitle());
                ps.setString(4, choice.getDescription() == null ? "" : choice.getDescription());
                ps.setInt(5, i);
                ps.setString(6, pollID);
            } else {
                query = "INSERT INTO choices (id, poll_id, title, description) VALUES (?,?,?,?)";
                ps    = connection.prepareStatement(query);
                ps.setInt(1, i);
                ps.setString(2, pollID);
                ps.setString(3, choice.getTitle());
                ps.setString(4, choice.getDescription() == null ? "" : choice.getDescription());
            }
        }
        ps.executeUpdate();
    }

    private void updateAllChoices(Poll poll) throws SQLException {

        int bound = poll.getChoices().size();
        for (int i = 0; i < bound; i++) {
            updateChoice(poll.getChoices().get(i), i, poll.getPollID());
        }

        Connection connection = DBConnection.getConnection();

        String deleteQuery = "DELETE FROM choices WHERE poll_id=? AND id >= ?";

        PreparedStatement ps = connection.prepareStatement(deleteQuery);
        ps.setString(1, poll.getPollID());
        ps.setInt(2, bound);
        ps.executeUpdate();
    }

    private void updateVote(String pollID, String pin, int choiceID) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String updateQuery = "UPDATE votes SET choice_id=? WHERE poll_id=? AND pin=?";


        PreparedStatement ps = connection.prepareStatement(updateQuery);
        ps.setInt(1, choiceID);
        ps.setString(2, pollID);
        ps.setString(3, pin);
        ps.executeUpdate();
    }

    private void updateAllVotes(Poll poll) throws SQLException {

        for (Map.Entry<String, Choice> entry : poll.getParticipantVotes().entrySet()) {
            String pin = entry.getKey();
            Choice choice = entry.getValue();
            updateVote(poll.getPollID(), pin, poll.getChoices().indexOf(choice));
        }

        Connection connection = DBConnection.getConnection();

        String deleteQuery = "DELETE FROM votes WHERE poll_id=? AND pin NOT IN " + poll.getParticipantVotes().keySet().stream().collect(Collectors.joining("','", "('", "')"));

        PreparedStatement ps = connection.prepareStatement(deleteQuery);
        ps.setString(1, poll.getPollID());
        ps.executeUpdate();
    }
}