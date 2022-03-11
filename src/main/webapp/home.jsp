<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.myservlet.Model.IEMDBController"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
    <ul>
        <li id="email">Email: <%=IEMDBController.getInstance().getActive_user().getEmail()%></li>
        <li>
            <a href="/movies">Movies</a>
        </li>
        <li>
            <a href="/watchlist">Watch List</a>
        </li>
        <li>
            <a href="/logout">Log Out</a>
        </li>
    </ul>
</body>
</html>