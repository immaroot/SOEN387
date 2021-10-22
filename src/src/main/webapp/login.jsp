<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poll</title>
    <link href="/assets/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>
<%
    boolean error = false;
    if (request.getMethod().equals("POST")) {
        String password = request.getParameter("pw");
        if ("1234".equals(password)) {
            session.setAttribute("loggedIn", true);
            response.sendRedirect("/manager.jsp");
            return;
        }
        error = true;
    }
%>
<body>
<div class="container pt-5">
    <h2 class="text-center">Manager Login</h2>
    <form method="post">
        <div class="form-inline">
            <label class="mr-2">Password: </label>
            <input type="password" class="form-control mr-3" name="pw">
            <button class="btn btn-primary" type="submit">Login</button>
        </div>
    </form>
    <% if (error) { %>
    <div class="alert alert-danger mt-5" role="alert">
        Wrong password. Please type correct password.
    </div>
    <% } %>
</div>
</body>
</html>
