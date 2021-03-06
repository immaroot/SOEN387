package ca.concordia.poll.app.controllers;

import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.Choice;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.services.PollRepository;
import ca.concordia.poll.core.users.PollManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UpdatePollServlet", value = "/admin/update")
public class UpdatePollServlet extends HttpServlet {

    Poll poll;
    PollRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = (PollRepository) getServletContext().getAttribute("pollRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pollID = request.getParameter("poll");

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

            request.setAttribute("poll", poll);

            response.setContentType("text/html");

            RequestDispatcher rd = request.getRequestDispatcher("./update_poll.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pollID = request.getParameter("poll");

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

            String name = request.getParameter("name");
            String question = request.getParameter("question");
            String[] options = request.getParameterValues("choice");
            String[] descriptions = request.getParameterValues("description");

            List<Choice> choices = new ArrayList<>();

            for (int i = 0; i < options.length; i++) {
                choices.add(new Choice(options[i], descriptions[i]));
            }

            try {
                pollManager.updatePoll(name, question, choices);
            } catch (PollException e) {
                throw new ServletException(e);
            }

            repository.save(poll);

            response.setContentType("text/html");

            response.sendRedirect(getServletContext().getContextPath() + "/admin");
        }
    }
}
