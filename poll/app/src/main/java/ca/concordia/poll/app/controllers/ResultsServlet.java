package ca.concordia.poll.app.controllers;

import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.services.PollRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "ResultsServlet", value = "/results/*")
public class ResultsServlet extends HttpServlet {

    Poll poll;
    PollRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = (PollRepository) getServletContext().getAttribute("pollRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pollID = request.getPathInfo().replaceFirst("/", "");

        try {
            poll = repository.findById(pollID);
        } catch (PollException e) {
            e.printStackTrace();
            throw new ServletException("No poll with that id.");
        }

        response.setContentType("text/html");

        HashMap<String, Integer> results;
        try {
            results = new HashMap<>(poll.getResults());
            request.setAttribute("results", results);
            request.setAttribute("poll", poll);
            RequestDispatcher rd = request.getRequestDispatcher("/results.jsp");
            rd.forward(request, response);
        } catch (PollException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
