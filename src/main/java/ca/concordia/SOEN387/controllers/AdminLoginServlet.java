package ca.concordia.SOEN387.controllers;

import ca.concordia.SOEN387.models.PollManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdminLoginServlet", value = "/login")
public class AdminLoginServlet extends HttpServlet {

    String managerPass = "secretpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String password = request.getParameter("password");

        System.out.println(password);

        if (password != null && password.equals(managerPass)) {

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("admin", true);
            newSession.setMaxInactiveInterval(60*10);

            newSession.setAttribute("user", new PollManager("Joe"));

            response.sendRedirect(getServletContext().getContextPath() + "/admin");

        } else {
            request.setAttribute("message", "Wrong password please try again!");
            response.sendRedirect(getServletContext().getContextPath() + "/login");
        }
    }
}
