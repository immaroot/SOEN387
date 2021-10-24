package ca.concordia.SOEN387.controllers;

import ca.concordia.SOEN387.models.Participant;
import ca.concordia.SOEN387.models.Poll;
import ca.concordia.SOEN387.models.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

@WebFilter(filterName = "UserFilter")
public class UserFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Cookie[] cookies = req.getCookies();

        String sessionId = null;
        Participant user;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    sessionId = cookie.getValue();
                }
            }
        }

        if (sessionId == null) {
            sessionId = generateSessionId();
            Cookie c = new Cookie("sessionId", sessionId);
            resp.addCookie(c);
        }

        user = new Participant(sessionId);
        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);

        System.out.println("User: " + user.getName() + " is requesting: " + req.getRequestURI());

        chain.doFilter(request, response);
    }

    private static String generateSessionId() {
        String uid = UUID.randomUUID().toString();
        return URLEncoder.encode(uid);
    }
}
