package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.exceptions.UserManagementException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordSecurity {

    public static String getHash(String password) throws UserManagementException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new UserManagementException(" MessageDigest error: " + e.getMessage());
        }
        md.update(password.getBytes());
        byte[] digest = md.digest();

        return Arrays.toString(digest);
    }

    public static boolean verify(String rawPassword, String hashedPassword) throws UserManagementException {
        return hashedPassword.equals(getHash(rawPassword));
    }
}
