package ca.concordia.poll.app.controllers;

import ca.concordia.poll.core.Choice;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.services.PollRepository;
import ca.concordia.poll.core.users.PollManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreatePollServlet", value = "/admin/create")
public class CreatePollServlet extends HttpServlet {

    PollRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = (PollRepository) getServletContext().getAttribute("pollRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("create_poll.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String question = request.getParameter("question");
        String[] options = request.getParameterValues("choice");
        String[] descriptions = request.getParameterValues("description");

        List<Choice> choices = new ArrayList<>();

        for (int i = 0; i < options.length; i++) {
            choices.add(new Choice(options[i], descriptions[i]));
        }

        PollManager pollManager = (PollManager) request.getSession(false).getAttribute("user");
        Poll poll = new Poll();
        poll.setCreatedBy(pollManager.getUserID());

        pollManager.setPoll(poll);

        try {
            pollManager.createPoll(name, question, choices);
        } catch (PollException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }

        repository.save(poll);

        response.setContentType("text/html");
        response.sendRedirect(getServletContext().getContextPath() + "/admin");
    }
}
