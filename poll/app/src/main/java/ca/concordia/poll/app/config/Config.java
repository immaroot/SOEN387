package ca.concordia.poll.app.config;

import ca.concordia.poll.app.auth.AppUserManager;
import ca.concordia.poll.app.services.AppPollRepository;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.IOException;
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

        String host = config.getProperty("email_host");
        int port = Integer.parseInt(config.getProperty("email_port"));
        String username = config.getProperty("email_username");
        String password = config.getProperty("email_password");

        String appURL = config.getProperty("app_url") + sce.getServletContext().getContextPath();

        AppUserManager userManager = new AppUserManager(host, port, username, password);
        userManager.setAppURL(appURL);

        AppPollRepository pollRepository = new AppPollRepository();

        sce.getServletContext().setAttribute("userManager", userManager);
        sce.getServletContext().setAttribute("pollRepository", pollRepository);

        System.out.println("HELLO FROM CONFIG!!!");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
