package ca.concordia.poll.app.config;

import ca.concordia.poll.app.auth.UserManager;
import ca.concordia.poll.app.auth.UserRepository;
import ca.concordia.poll.app.services.AppPollRepository;
import ca.concordia.poll.core.Choice;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.PollStatus;
import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;
import ca.concordia.poll.core.users.PollManager;
import clover.com.google.gson.Gson;
import clover.com.google.gson.GsonBuilder;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Config implements ServletContextListener {



    private final Properties config = new Properties();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("Should be executed only once.");

        ServletContextListener.super.contextInitialized(sce);
        try {
            config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sce.getServletContext().setAttribute("config", this);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath() + getProperty("users_file")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PollManager[] managers = gson.fromJson(bufferedReader, PollManager[].class);

        UserRepository userRepository = new UserRepository();

        for (AuthenticatedUser user : managers) {
            try {
                userRepository.saveAuthenticatedUser(user);
            } catch (UserManagementException e) {
                e.printStackTrace();
            }
        }

        AppPollRepository pollRepository = new AppPollRepository();
        UserManager userManager = new UserManager(userRepository);
//        Poll poll = createTestPoll();
//
//        pollRepository.save(poll);

        sce.getServletContext().setAttribute("userRepository", userRepository);
        sce.getServletContext().setAttribute("userManager", userManager);
        sce.getServletContext().setAttribute("pollRepository", pollRepository);

        System.out.println("HELLO FROM CONFIG!!!");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }

    public static Config getInstance(ServletContext context) {
        return (Config) context.getAttribute("config");
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }

    private Poll createTestPoll() {
        Choice option1 = new Choice("banana");
        Choice option2 = new Choice("raspberries");
        Choice option3 = new Choice("apple");
        Choice option4 = new Choice("cherries");
        Choice option5 = new Choice("peaches");

        List<Choice> choices = new ArrayList<>();


        choices.add(option1);
        choices.add(option2);
        choices.add(option3);
        choices.add(option4);
        choices.add(option5);

        Poll poll = new Poll();
        try {
            poll.create("Favorite Fruits", "What is your favorite fruit?", choices);
        } catch (PollException e) {
            e.printStackTrace();
        }
        poll.setStatus(PollStatus.RUNNING);
        return poll;
    }
}
