package ca.concordia.SOEN387.controllers;

import ca.concordia.SOEN387.exceptions.PollException;
import ca.concordia.SOEN387.models.Choice;
import ca.concordia.SOEN387.models.Poll;
import ca.concordia.SOEN387.models.PollManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "VoteServlet", value = "/poll")
public class VoteServlet extends HttpServlet {
    PollManager manager;
    Poll poll;

    @Override
    public void init() throws ServletException {
        super.init();
        manager = new PollManager("Joe");
        poll = manager.getPoll();

        Choice option1 = new Choice("banana");
        Choice option2 = new Choice("raspberries");
        Choice option3 = new Choice("apple");
        Choice option4 = new Choice("cherries");
        Choice option5 = new Choice("peaches");

        List<Choice> choices = new ArrayList<>();

        choices.add(option1);
        choices.add(option2);
        choices.add(option3);
        choices.add(option3);
        choices.add(option4);
        choices.add(option5);

        try {
            manager.createPoll("Favorite Fruits", "What is your favorite fruit?", choices);
        } catch (PollException e) {
            e.printStackTrace();
        }

        try {
            manager.runPoll();
        } catch (PollException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("./poll.jsp");
        request.setAttribute("poll", poll);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("./success_vote.jsp");
        rd.forward(request, response);
    }
}
