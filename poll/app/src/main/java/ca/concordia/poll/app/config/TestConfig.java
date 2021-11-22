package ca.concordia.poll.app.config;

import ca.concordia.poll.app.auth.UserManager;
import ca.concordia.poll.app.auth.UserRepository;
import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.PollManager;
import clover.com.google.gson.Gson;
import clover.com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class TestConfig {

    public static void main(String[] args) throws IOException, UserManagementException {
        Properties props = new Properties();
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "app.properties";
        System.out.println(appConfigPath);

        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userJsonFilePath = rootPath + props.getProperty("users_file");

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(userJsonFilePath));

        PollManager[] managers = gson.fromJson(bufferedReader, PollManager[].class);

        System.out.println(props.getProperty("users_file"));

        UserRepository repository = new UserRepository();

        assert managers != null;
        for (PollManager pollManager : managers) {
            repository.saveAuthenticatedUser(pollManager);
            System.out.println(pollManager);
        }

        UserManager userManager = new UserManager(repository);

        PollManager manager = (PollManager) userManager.login("user@example.com", "wrong pass");

        System.out.println(manager);
    }
}
