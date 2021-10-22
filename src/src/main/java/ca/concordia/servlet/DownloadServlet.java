package ca.concordia.servlet;

import ca.concordia.service.PollService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PollService pollService = PollService.getInstance();
        String name = request.getParameter("name");
        String format = request.getParameter("format");

        pollService.downloadPollDetails(response, name, format);
    }
}
