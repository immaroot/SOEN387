package ca.concordia.poll.app.controllers;

import ca.concordia.poll.core.exceptions.PollException;
import ca.concordia.poll.core.Poll;
import ca.concordia.poll.core.services.PollRepository;
import ca.concordia.poll.core.users.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Date;

@WebServlet(name = "DownloadResultsServlet", value = "/download/*")
public class DownloadResultsServlet extends HttpServlet {

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
        }

        String pollTitle = poll.getTitle();
        String date = Date.from(Instant.now()).toString();
        String filename = pollTitle + "-" + date + ".txt";

        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);

        User user = new User();

        OutputStream out = response.getOutputStream();
        PrintWriter pw = new PrintWriter(out);

        try {
            user.downloadPollDetails(pw, filename);
        } catch (PollException e) {
            e.printStackTrace();
        }
        pw.flush();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("Post requests are not permitted on this URI");
    }
}
