package ca.concordia.poll.usermanagement;

public class Token {

    private String token;

    private boolean verified;

    public Token(String token) {
        this.token = token;
        this.verified = false;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
