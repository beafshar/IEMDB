<%@ page import="com.myservlet.Model.IEMDBController" %>
<%@ page import="com.myservlet.Model.Actor" %>
<%@ page import="com.myservlet.Model.Movie" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Actor</title>
    <style>
      li, td, th {
        padding: 5px;
      }
    </style>
</head>
<body>
    <%
        String actor_id = (String)request.getAttribute("actor_id");
        Actor actor = IEMDBController.actorHandler.actors.get(Integer.parseInt(actor_id));
    %>
    <a href="/">Home</a>
    <p id="email">Email: <%=IEMDBController.getInstance().getActive_user().getEmail()%></p>
    <ul>
        <li id="name">name:  <%= actor.getName() %></li>
        <li id="birthDate">birthDate: <%= actor.getBirthDate() %></li>
        <li id="nationality">nationality: <%= actor.getNationality() %></li>
        <li id="tma">Total movies acted in: <%= actor.getMovies().size() %></li>
    </ul>
    <table>
        <tr>
            <th>Movie</th>
            <th>imdb Rate</th> 
            <th>rating</th> 
            <th>page</th> 
        </tr>
        <%
            for (Movie movie : actor.getMovies()) {
        %>
        <tr>
            <td><%= movie.getName() %></td>
            <td><%= movie.getImdbRate() %></td>
            <td><%= movie.getRating() %></td>
            <td><a href=<%= "/movies/" + new DecimalFormat("00").format(movie.getId()) %>>Link</a></td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>