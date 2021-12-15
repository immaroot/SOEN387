package ca.concordia.poll.app.controllers;

import ca.concordia.poll.core.*;
import ca.concordia.poll.core.exceptions.ClosedPollException;
import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.exceptions.WrongChoicePollException;
import ca.concordia.poll.core.services.PollRepository;
import ca.concordia.poll.core.users.Participant;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "VoteServlet", value = "/poll/*")
public class VoteServlet extends HttpServlet {
    Poll poll;
    PollRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = (PollRepository) getServletContext().getAttribute("pollRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        String pollID = request.getPathInfo().replaceFirst("/", "");

        try {
            poll = repository.findById(pollID);
        } catch (PollException e) {
            e.printStackTrace();
        }

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

        response.setContentType("text/html");

        String pollID = request.getPathInfo().replaceFirst("/", "");

        try {
            poll = repository.findById(pollID);
        } catch (PollException e) {
            e.printStackTrace();
        }

        if (poll.isOpen() && poll.getStatus() == PollStatus.RUNNING) {

            Participant user = new Participant();
            user.setPoll(poll);

            String voteOption = request.getParameter("choice");
            String inputPin = request.getParameter("pin");
            Choice choice = null;
            for (Choice option : poll.getChoices()) {
                if (option.getTitle().equals(voteOption)) {
                    choice = option;
                    break;
                }
            }

            if (choice != null) {

                if (!inputPin.isEmpty()) {
                    try {
                        user.updateVote(inputPin, choice);
                        repository.save(poll);
                        request.setAttribute("vote", choice);
                        request.setAttribute("pin", inputPin);
                        RequestDispatcher rd = request.getRequestDispatcher("/updated_vote.jsp");
                        rd.forward(request, response);
                    } catch (PollException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String pin = user.vote(choice);
                        repository.save(poll);
                        request.setAttribute("vote", choice);
                        request.setAttribute("pin", pin);
                        RequestDispatcher rd = request.getRequestDispatcher("/success_vote.jsp");
                        rd.forward(request, response);
                    } catch (PollException e) {
                        throw new ServletException(e);
                    }
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
        RequestDispatcher rd = request.getRequestDispatcher("/results/" + poll.getPollID());
        rd.forward(request, response);
    }

    private void pollRunning(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/poll.jsp");
        request.setAttribute("poll", poll);
        rd.forward(request, response);
    }

    private void pollCreated(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/no_poll.jsp");
        rd.forward(request, response);
    }

    private void pollClosed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/no_poll.jsp");
        rd.forward(request, response);
    }
}