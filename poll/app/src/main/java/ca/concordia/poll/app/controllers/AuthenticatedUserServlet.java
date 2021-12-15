package ca.concordia.poll.app.controllers;

import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.services.PollRepository;
import ca.concordia.poll.core.users.PollManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AuthenticatedUserServlet extends HttpServlet {

    List<Poll> polls;
    PollRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = (PollRepository) getServletContext().getAttribute("pollRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pollID = request.getParameter("poll");

        RequestDispatcher rd;
        if (pollID == null || pollID.equals("")) {
            PollManager pollManager = (PollManager) request.getSession(false).getAttribute("user");

            polls = repository.getAllPollsForAuthenticatedUser(pollManager.getUserID());

            request.setAttribute("polls", polls);

            response.setContentType("text/html");
            rd = request.getRequestDispatcher("admin/polls.jsp");
        } else {
            Poll poll;
            try {
                poll = repository.findById(pollID);
            } catch (PollException e) {
                e.printStackTrace();
                throw new ServletException(e.getMessage());
            }
            request.setAttribute("poll", poll);
            rd = request.getRequestDispatcher("admin/admin.jsp");
        }

        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pollID = request.getParameter("poll");

        Poll poll;
        if (pollID == null || pollID.equals("")) {
            throw new ServletException("pollID is not given");
        } else {
            PollManager pollManager = (PollManager) request.getSession(false).getAttribute("user");

            try {
                poll = repository.findById(pollID);
            } catch (PollException e) {
                throw new ServletException(e.getMessage());
            }

            if (poll.getCreatedBy() == pollManager.getUserID()) {
                pollManager.setPoll(poll);
            } else {
                throw new ServletException("Wrong manager. This poll does not belong to you.");
            }

            String action = request.getParameter("action");
            String redirectPage = "/admin";

            System.out.println("The manager id is " + pollManager.getUserID());
            System.out.println("The action is " + action);
            System.out.println("The poll is in status  " + poll.getStatus().toString());

            switch (action) {
                case "create":
                    redirectPage = "/admin/create";
                    break;
                case "update":
                    redirectPage = "/admin/update?poll=" + pollID;
                    break;
                case "release":
                    try {
                        pollManager.releasePoll();
                        repository.save(poll);
                    } catch (PollException e) {
                        throw new ServletException(e);
                    }
                    break;
                case "run":
                    try {
                        pollManager.runPoll();
                        repository.save(poll);
                    } catch (PollException e) {
                        throw new ServletException(e);
                    }
                    break;
                case "clear":
                    try {
                        pollManager.clearPoll();
                        repository.save(poll);
                    } catch (PollException e) {
                        throw new ServletException(e);
                    }
                    break;
                case "unrelease":
                    try {
                        pollManager.unreleasePoll();
                        repository.save(poll);
                    } catch (PollException e) {
                        throw new ServletException(e);
                    }
                    break;
                case "close":
                    try {
                        pollManager.closePoll();
                        repository.save(poll);
                    } catch (PollException e) {
                        throw new ServletException(e);
                    }
                    break;
                default:
                    throw new ServletException("Action not found.");
            }
            response.sendRedirect(getServletContext().getContextPath() + redirectPage);
        }
    }
}
