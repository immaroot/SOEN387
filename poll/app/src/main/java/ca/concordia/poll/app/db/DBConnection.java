package ca.concordia.poll.app.db;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

    static String DB_URL;
    static String DB_NAME;
    static String DB_USER;
    static String DB_PASSWORD;

    static {
        Properties props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DB_URL = props.getProperty("db_url");
        DB_NAME = props.getProperty("db_name");
        DB_USER = props.getProperty("db_user");
        DB_PASSWORD = props.getProperty("db_pass");


    }
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
