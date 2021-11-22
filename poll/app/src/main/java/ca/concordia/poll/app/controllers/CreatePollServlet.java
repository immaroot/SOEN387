package ca.concordia.poll.app.controllers;

import ca.concordia.poll.core.users.PollManager;
import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.Choice;
import ca.concordia.poll.core.Poll;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreatePollServlet", value = "/admin/create")
public class CreatePollServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("create_poll.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Poll poll = Poll.getInstance();
        String name = request.getParameter("name");
        String question = request.getParameter("question");
        String[] options = request.getParameterValues("choice");
        String[] descriptions = request.getParameterValues("description");

        List<Choice> choices = new ArrayList<>();

        for (int i = 0; i < options.length; i++) {
            choices.add(new Choice(options[i], descriptions[i]));
        }

//        try {
//            poll.create(name, question, choices, (PollManager) request.getServletContext().getAttribute("manager"));
//        } catch (PollException e) {
//            throw new ServletException(e);
//        }

        response.setContentType("text/html");
        response.sendRedirect(getServletContext().getContextPath() + "/admin");
    }
}
