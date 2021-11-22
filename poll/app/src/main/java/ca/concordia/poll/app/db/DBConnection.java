package ca.concordia.poll.app.db;

import java.sql.*;

public class DBConnection {

    static final String DB_URL = "jdbc:postgresql://localhost/";
    static final String DB_NAME = "postgres";
    static final String DB_USER = "postgres";
    static final String DB_PASSWORD = "secret";

    static Connection connection = null;

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
            System.out.println("Connected to DB!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
