package ca.concordia.poll.app.auth;

import ca.concordia.poll.app.db.DBConnection;
import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;
import ca.concordia.poll.usermanagement.PasswordSecurity;
import ca.concordia.poll.usermanagement.UserRepository;

import java.sql.*;

public class AppUserRepository implements UserRepository {

    @Override
    public AuthenticatedUser saveAuthenticatedUser(AuthenticatedUser newUser) throws UserManagementException {
        String hashedPassword = PasswordSecurity.getHash(newUser.getPassword());

        String query = "INSERT INTO users (full_name, email, password) VALUES (?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, newUser.getFullName());
            ps.setString(2, newUser.getEmail());
            ps.setString(3, hashedPassword);

            if (ps.executeUpdate() == 1) {
               try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                   if (generatedKeys.next()) {
                       newUser.setUserID(generatedKeys.getInt(1));
                       return newUser;
                   } else {
                       throw new SQLException("Failed to create a new id for user.");
                   }
               }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public AuthenticatedUser findAuthenticatedUserByEmail(String email) throws UserManagementException {
        String query = "SELECT id, full_name, email, password, enabled FROM users WHERE email=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private AuthenticatedUser extractUserFromResultSet(ResultSet rs) throws SQLException {
        AuthenticatedUser user = new AuthenticatedUser();
        user.setUserID(rs.getInt("id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    }

    public static void main(String[] args) throws UserManagementException {


        AuthenticatedUser user = new AuthenticatedUser();

        user.setFullName("Bob");
        user.setEmail("bob@eexample.com");
        user.setPassword("secret");

        AppUserRepository repo = new AppUserRepository();

        AuthenticatedUser savedUser = repo.saveAuthenticatedUser(user);

        if (savedUser != null) {
            System.out.println(savedUser.getUserID());

            System.out.println(savedUser);
        }

        System.out.println(repo.findAuthenticatedUserByEmail("bob@eexample.com"));
    }
}
