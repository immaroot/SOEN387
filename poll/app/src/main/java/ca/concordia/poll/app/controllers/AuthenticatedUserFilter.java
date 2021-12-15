package ca.concordia.poll.app.controllers;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthenticatedUserFilter")
public class AuthenticatedUserFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean isAuthenticated;
        try {
            isAuthenticated = (boolean) session.getAttribute("authenticated");
            System.out.println("Authenticated? : " + isAuthenticated);
        } catch (NullPointerException e){
            isAuthenticated = false;
            System.out.println("Authenticated? : null");
        }

        if (session == null || !isAuthenticated) {
            System.out.println("Unauthorized access denied.");
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            chain.doFilter(request, response);
        }
    }
}
