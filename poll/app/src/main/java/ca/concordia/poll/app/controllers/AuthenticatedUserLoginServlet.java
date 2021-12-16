package ca.concordia.poll.app.controllers;

import ca.concordia.poll.app.auth.AppUserManager;
import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;
import ca.concordia.poll.core.users.PollManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdminLoginServlet", value = "/login")
public class AuthenticatedUserLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        boolean isAuthenticated;
        try {
            isAuthenticated = (boolean) session.getAttribute("authenticated");
        } catch (NullPointerException e){
            isAuthenticated = false;
        }
        if (isAuthenticated) {
            response.sendRedirect(getServletContext().getContextPath() + "/admin");
        } else {
            response.setContentType("text/html");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        AppUserManager userManager = (AppUserManager) getServletContext().getAttribute("userManager");

        AuthenticatedUser user;
        PollManager manager = null;
        try {
            user = userManager.login(email, password);
            manager = new PollManager();
            manager.setUserID(user.getUserID());
            manager.setEmail(user.getEmail());
            manager.setPassword(user.getPassword());
            manager.setFullName(user.getFullName());

        } catch (UserManagementException e) {
            e.printStackTrace();
        }

        if (manager != null) {

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("authenticated", true);
            newSession.setAttribute("user", manager);
            newSession.setMaxInactiveInterval(60*10);

            response.sendRedirect(getServletContext().getContextPath() + "/admin");

        } else {
            request.setAttribute("message", "Wrong password please try again!");
            response.sendRedirect(getServletContext().getContextPath() + "/login");
        }
    }
}
