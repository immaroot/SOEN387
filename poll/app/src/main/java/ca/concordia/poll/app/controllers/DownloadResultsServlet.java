//package ca.concordia.poll.app.controllers;
//
//import ca.concordia.poll.core.exceptions.PollException;
//import ca.concordia.poll.core.Poll;
//import ca.concordia.poll.core.User;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.time.Instant;
//import java.util.Date;
//
//@WebServlet(name = "DownloadResultsServlet", value = "/download")
//public class DownloadResultsServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String pollTitle = Poll.getInstance().getName();
//        String date = Date.from(Instant.now()).toString();
//        String filename = pollTitle + "-" + date + ".txt";
//
//        response.setContentType("text/plain");
//        response.setHeader("Content-disposition", "attachment; filename=" + filename);
//
//        User user = (User) request.getSession(false).getAttribute("user");
//
//        if (user == null) {
//            throw new ServletException("No user is tracked.. Should not happen");
//        }
//
//        OutputStream out = response.getOutputStream();
//        PrintWriter pw = new PrintWriter(out);
//
//        try {
//            user.downloadPollDetails(pw, filename);
//        } catch (PollException e) {
//            e.printStackTrace();
//        }
//        pw.flush();
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        throw new ServletException("Post requests are not permitted on this URI");
//    }
//}
