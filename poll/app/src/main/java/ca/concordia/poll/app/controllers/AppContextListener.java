//package ca.concordia.poll.app.controllers;
//
//import ca.concordia.poll.core.users.PollManager;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//
//@WebListener
//public class AppContextListener implements ServletContextListener, HttpSessionListener {
//
//    public AppContextListener() {
//    }
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
//        PollManager manager = new PollManager("Joe");
//        sce.getServletContext().setAttribute("manager", manager);
//        System.out.println("Starting App and we have a manager called Joe!");
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
//        System.out.println("Closing the App now.");
//    }
//
//    @Override
//    public void sessionCreated(HttpSessionEvent se) {
//        System.out.println("New session created with id: " + se.getSession().getId());
//    }
//
//    @Override
//    public void sessionDestroyed(HttpSessionEvent se) {
//        System.out.println("Session being destroyed with id: " + se.getSession().getId());
//    }
//}
