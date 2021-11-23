<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>


<%@page isErrorPage="true" %>



<h2>
    Ohoh there was an error: <%= exception %>
</h2>


</body>
</html>
