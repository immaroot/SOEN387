package ca.concordia.poll.app.dao;

import ca.concordia.poll.core.users.AuthenticatedUser;

public interface UserDAO {

    AuthenticatedUser getUserByEmail();
}
