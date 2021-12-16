package ca.concordia.poll.app.controllers;

import ca.concordia.poll.app.auth.AppUserManager;
import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("repassword");

        if (!password.equals(rePassword)) {
            throw new ServletException("The passwords do not match");
        }

        AppUserManager userManager = (AppUserManager) getServletContext().getAttribute("userManager");

        AuthenticatedUser newUser = new AuthenticatedUser();
        newUser.setEmail(email);
        newUser.setFullName(name);
        newUser.setPassword(password);

        AuthenticatedUser savedUser;
        try {
            savedUser = userManager.register(newUser);
        } catch (UserManagementException e) {
            e.printStackTrace();
            throw new ServletException("UserManagementException: " + e.getMessage());
        }

        if (savedUser != null) {
            RequestDispatcher rd = request.getRequestDispatcher("inform_verification.jsp");
            rd.forward(request, response);
        } else {
            request.setAttribute("message", "It was not possible to register.");
            response.sendRedirect(getServletContext().getContextPath() + "/register");
        }
    }
}
