package ca.concordia.SOEN387.controllers;

import ca.concordia.SOEN387.exceptions.PollException;
import ca.concordia.SOEN387.models.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "VoteServlet", value = "/poll")
public class VoteServlet extends HttpServlet {
    PollManager manager;
    Poll poll;
    HashMap<Integer, Choice> choiceHashMap;

    @Override
    public void init() throws ServletException {
        super.init();
        manager = new PollManager("Joe");
        poll = manager.getPoll();
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

        Cookie[] cookies = request.getCookies();

        String sessionId = null;
        Participant user;


        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("sessionId")) {
                sessionId = cookie.getValue();
            }
        }

        if (sessionId == null) {
            sessionId = generateSessionId();
            Cookie c = new Cookie("sessionId", sessionId);
            response.addCookie(c);
        }

        user = new Participant(sessionId, poll);
        String voteOption = request.getParameter("option");
        Choice choice = null;
        for (Choice option : poll.getChoices()) {
            if (option.getTitle().equals(voteOption)) {
                choice = option;
            }
        }

        if (choice != null) {
            try {
                user.vote(choice);
                response.setContentType("text/html");
                request.setAttribute("vote", choice);
                RequestDispatcher rd = request.getRequestDispatcher("./success_vote.jsp");
                rd.forward(request, response);
            } catch (PollException e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        } else {
            System.out.println("Could not find the choice..");
        }
    }

    private static String generateSessionId() {
        String uid = UUID.randomUUID().toString();
        return URLEncoder.encode(uid);
    }
}
