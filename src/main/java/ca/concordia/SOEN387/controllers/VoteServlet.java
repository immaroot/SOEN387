package ca.concordia.SOEN387.controllers;

import ca.concordia.SOEN387.exceptions.ClosedPollException;
import ca.concordia.SOEN387.exceptions.PollException;
import ca.concordia.SOEN387.exceptions.WrongChoicePollException;
import ca.concordia.SOEN387.models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "VoteServlet", value = "/")
public class VoteServlet extends HttpServlet {
    PollManager manager;
    Poll poll;
    HashMap<Integer, Choice> choiceHashMap;

    @Override
    public void init() throws ServletException {
        super.init();
        manager = (PollManager) getServletContext().getAttribute("manager");
        System.out.println("From VoteServlet we have the pollmanager called: " + manager.getName());

        poll          = manager.getPoll();
        choiceHashMap = new HashMap<>();

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

        int i = 0;
        for (Choice choice : choices) {
            choiceHashMap.put(i++, choice);
        }

        try {
            manager.createPoll("Favorite Fruits", "What is your favorite fruit?", choices);
        } catch (PollException e) {
            throw new ServletException(e);
        }

        try {
            manager.runPoll();
        } catch (PollException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        if (poll.isOpen()) {
            PollStatus status = poll.getStatus();
            switch (status) {
                case CREATED:
                    pollCreated(request, response);
                    break;
                case RUNNING:
                    pollRunning(request, response);
                    break;
                case RELEASED:
                    pollReleased(request, response);
                    break;
                default:
                    throw new ServletException("Don't know what happened.... :(");
            }
        } else {
            pollClosed(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (poll.isOpen() && poll.getStatus() == PollStatus.RUNNING) {

            Participant user = (Participant) request.getSession(false).getAttribute("user");

            if (user == null) {
                throw new ServletException("No user is tracked.. Should not happen");
            }

            String voteOption = request.getParameter("choice");
            Choice choice = null;
            for (Choice option : poll.getChoices()) {
                if (option.getTitle().equals(voteOption)) {
                    choice = option;
                    break;
                }
            }

            if (choice != null) {
                try {
                    user.vote(choice);
                    System.out.println("The user with id: " + user.getName() + " just voted.");
                    response.setContentType("text/html");
                    request.setAttribute("vote", choice);
                    RequestDispatcher rd = request.getRequestDispatcher("./success_vote.jsp");
                    rd.forward(request, response);
                } catch (PollException e) {
                    throw new ServletException(e);
                }
            } else {
                try {
                    throw new WrongChoicePollException("The choice selected does not exists..");
                } catch (WrongChoicePollException e) {
                    throw new ServletException(e);
                }
            }
        } else {
            try {
                throw new ClosedPollException("The poll is not yet opened or not RUNNING.");
            } catch (ClosedPollException e) {
                throw new ServletException(e);
            }
        }
    }

    private void pollReleased(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/results");
        rd.forward(request, response);
    }

    private void pollRunning(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("./poll.jsp");
        request.setAttribute("poll", poll);
        rd.forward(request, response);
    }

    private void pollCreated(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("./no_poll.jsp");
        rd.forward(request, response);
    }

    private void pollClosed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("./no_poll.jsp");
        rd.forward(request, response);
    }
}