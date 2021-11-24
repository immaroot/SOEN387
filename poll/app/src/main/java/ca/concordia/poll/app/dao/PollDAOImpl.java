package ca.concordia.poll.app.dao;

import ca.concordia.poll.app.db.DBConnection;
import ca.concordia.poll.core.*;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class PollDAOImpl implements PollDAO {

    @Override
    public Optional<Poll> get(String pollID) {
        String query = "SELECT id, user_id, title, question, status, state FROM polls WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pollID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(extractPollFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Poll> getAll() {
        List<Poll> polls = new ArrayList<>();
        String query = "SELECT id, user_id, title, question, status, state FROM polls";
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

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
            String query = "INSERT INTO polls (id, user_id, title, question, status, state) VALUES (?,?,?,?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

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

    private void saveAllChoices(Poll poll) {
        for (Choice choice : poll.getChoices()) {
            saveChoice(poll, choice);
        }
    }

    private void saveChoice(Poll poll, Choice choice) {
        String query = "INSERT INTO choices (id, poll_id, title, description) VALUES (?,?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, poll.getChoices().indexOf(choice));
            ps.setString(2, poll.getPollID());
            ps.setString(3, choice.getTitle());
            ps.setString(4, choice.getDescription());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void saveAllVotes(Poll poll) {
        for (Map.Entry<String, Choice> entry : poll.getParticipantVotes().entrySet()) {
            String pin = entry.getKey();
            Choice choice = entry.getValue();
            saveVote(poll, pin, choice);
        }
    }

    private void saveVote(Poll poll, String pin, Choice choice) {
        String query = "INSERT INTO votes (poll_id, pin, choice_id) VALUES (?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, poll.getPollID());
            ps.setString(2, pin);
            ps.setInt(3, poll.getChoices().indexOf(choice));

            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Poll update(Poll poll) {
        String query = "UPDATE polls SET user_id=?, title=?, question=?, status=?, state=? WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private void updateAllChoices(Poll poll) {

        int bound = poll.getChoices().size();
        for (int i = 0; i < bound; i++) {
            updateChoice(poll, i);
        }
        deleteExtraChoices(poll, bound);
    }

    private void deleteExtraChoices(Poll poll, int bound) {
        String deleteQuery = "DELETE FROM choices WHERE poll_id=? AND id >= ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
            ps.setString(1, poll.getPollID());
            ps.setInt(2, bound);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateChoice(Poll poll, int i) {
        String query = "UPDATE choices SET title=?, description=? WHERE poll_id=? AND id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, poll.getChoices().get(i).getTitle());
            ps.setString(2, poll.getChoices().get(i).getDescription());
            ps.setString(3, poll.getPollID());
            ps.setInt(4, i);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateAllVotes(Poll poll) {
        for (Map.Entry<String, Choice> entry : poll.getParticipantVotes().entrySet()) {
            String pin = entry.getKey();
            Choice choice = entry.getValue();
            System.out.println(isUpdate(pin));
            if (isUpdate(pin)) {
                updateVote(poll, pin, choice);
            } else {
                saveVote(poll, pin, choice);
            }
        }
        deleteExtraVotes(poll);

    }

    private void deleteExtraVotes(Poll poll) {
        String deleteQuery = "DELETE FROM votes WHERE poll_id=? AND pin NOT IN " + poll.getParticipantVotes().keySet().stream().collect(Collectors.joining("','", "('", "')"));
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
            ps.setString(1, poll.getPollID());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateVote(Poll poll, String pin, Choice choice) {
        String updateQuery = "UPDATE votes SET choice_id=? WHERE poll_id=? AND pin=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateQuery)) {

            ps.setInt(1, poll.getChoices().indexOf(choice));
            ps.setString(2, poll.getPollID());
            ps.setString(3, pin);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean isUpdate(String pin) {
        boolean update = false;
        String query = "SELECT * FROM votes WHERE pin=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                update = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update;
    }

    @Override
    public List<Choice> getChoices(String pollID) {
        List<Choice> choices = new ArrayList<>();
        String query = "SELECT title, description FROM choices WHERE poll_id=? ORDER BY id";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pollID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                choices.add(new Choice(rs.getString("title"), rs.getString("description")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return choices;
    }

    @Override
    public Hashtable<Choice, Integer> getVotes(String pollID) {
        Hashtable<Choice, Integer> votes = new Hashtable<>();
        String query = "SELECT count(*), choice_id FROM votes WHERE poll_id=? GROUP BY choice_id";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pollID);
            ResultSet rs = ps.executeQuery();
            List<Choice> choices = getChoices(pollID);

            while (rs.next()) {
                votes.put(choices.get(rs.getInt("choice_id")), rs.getInt("count"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return votes;
    }

    @Override
    public Hashtable<String, Choice> getParticipantVotes(String pollID) {
        Hashtable<String, Choice> participantVotes = new Hashtable<>();
        String query = "SELECT pin, choice_id FROM votes WHERE poll_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pollID);
            ResultSet rs = ps.executeQuery();
            List<Choice> choices = getChoices(pollID);

            while (rs.next()) {
                participantVotes.put(rs.getString("pin"), choices.get(rs.getInt("choice_id")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return participantVotes;
    }

    @Override
    public List<Poll> getAllPollsForAuthenticatedUser(int userID) {
        List<Poll> polls = new ArrayList<>();
        String query = "SELECT id, user_id, title, question, status, state FROM polls WHERE user_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                polls.add(extractPollFromResultSet(rs));
            }
            return polls;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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
        poll.setVotes(getVotes(id));
        poll.setParticipantVotes(getParticipantVotes(id));

        return poll;
    }
}
