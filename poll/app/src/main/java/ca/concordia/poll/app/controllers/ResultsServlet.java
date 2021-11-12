package ca.concordia.poll.app.controllers;

import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.Poll;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "ResultsServlet", value = "/results")
public class ResultsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HashMap<String, Integer> results = null;
        try {
            results = new HashMap<>(Poll.getInstance().getResults());
            request.setAttribute("results", results);
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
