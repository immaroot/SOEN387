package ca.concordia.poll.app.auth;

import ca.concordia.poll.app.db.DBConnection;
import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;
import ca.concordia.poll.usermanagement.Token;
import ca.concordia.poll.usermanagement.TokenRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppTokenRepository implements TokenRepository {

    @Override
    public String getUserVerificationToken(AuthenticatedUser user) {
        String query = "SELECT token FROM tokens WHERE user_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, user.getUserID());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("token");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Token addVerificationTokenForUser(AuthenticatedUser user) {
        Token token = TokenRepository.generateToken();
        String query = "INSERT INTO tokens (token, user_id) VALUES (?,?)";
        try (Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, token.getToken());
            ps.setInt(2, user.getUserID());

            if (ps.executeUpdate() == 1) {
                return token;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isConfirmed(AuthenticatedUser user) {
        String query = "SELECT enabled FROM users WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, user.getUserID());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("enabled");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void verify(AuthenticatedUser user, String token) {
        String userToken = getUserVerificationToken(user);
        if (userToken.equals(token)) {
            String query = "UPDATE users SET enabled=? WHERE id=?";
            try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setBoolean(1, true);
                ps.setInt(2, user.getUserID());

                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws UserManagementException {

        AppUserRepository userRepository = new AppUserRepository();
        AppTokenRepository tokenRepository = new AppTokenRepository();

        AuthenticatedUser bob = userRepository.findAuthenticatedUserByEmail("bob@eexample.com");

        Token token = tokenRepository.addVerificationTokenForUser(bob);

        System.out.println(token.getToken());

        System.out.println("isConfirmed? : " + tokenRepository.isConfirmed(bob));

        tokenRepository.verify(bob, token.getToken());


        System.out.println("isConfirmed? : " + tokenRepository.isConfirmed(bob));
    }
}
