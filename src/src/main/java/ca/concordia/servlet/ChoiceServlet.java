package ca.concordia.servlet;

import ca.concordia.model.Result;
import ca.concordia.service.PollService;
import ca.concordia.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChoiceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int choice = Integer.parseInt(request.getParameter("choice"));
        HttpSession session = request.getSession();
        PollService pollService = PollService.getInstance();
        Result result = new Result();
        try {
            pollService.vote(session.getId(), choice);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }

        ServletUtil.respond(result, response);
    }
}
