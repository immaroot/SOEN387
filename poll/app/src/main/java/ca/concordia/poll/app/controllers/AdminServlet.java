package ca.concordia.poll.app.controllers;

import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.PollManager;
import ca.concordia.poll.core.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        Poll poll = Poll.getInstance();

        request.setAttribute("status", poll.getStatus().name());
        request.setAttribute("pollIsOpen", poll.isOpen());
        RequestDispatcher rd = request.getRequestDispatcher("admin/admin.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        User user = (User) request.getSession(false).getAttribute("user");

        PollManager manager = new PollManager(user.getName());

        System.out.println("The manager is " + manager.getName());

        Poll poll = Poll.getInstance();

        String action = request.getParameter("action");

        System.out.println("The action is " + action);

        System.out.println("The poll is in status  " + poll.getStatus().toString());

        String redirectPage = "/admin";

        switch (action) {
            case "create":
                redirectPage = "/admin/create";
                break;
            case "update":
                redirectPage = "/admin/update";
                break;
            case "release":
                try {
                    manager.releasePoll();
                } catch (PollException e) {
                    throw new ServletException(e);
                }
                break;
            case "run":
                try {
                    manager.runPoll();
                } catch (PollException e) {
                    throw new ServletException(e);
                }
                break;
            case "clear":
                try {
                    manager.clearPoll();
                } catch (PollException e) {
                    throw new ServletException(e);
                }
                break;
            case "unrelease":
                try {
                    manager.unreleasePoll();
                } catch (PollException e) {
                    throw new ServletException(e);
                }
                break;
            case "close":
                try {
                    manager.closePoll();
                } catch (PollException e) {
                    throw new ServletException(e);
                }
                break;
            case "logout":
                logoutAdmin(request);
                redirectPage = "/";
                break;
        }

        response.sendRedirect(getServletContext().getContextPath() + redirectPage);
    }

    private void logoutAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
    }
}
