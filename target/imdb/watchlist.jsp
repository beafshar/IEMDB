<%@ page import="com.myservlet.Model.IEMDBController" %>
<%@ page import="com.myservlet.Model.User" %>
<%@ page import="com.myservlet.Model.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WatchList</title>
    <style>
      li, td, th {
        padding: 5px;
      }
    </style>
</head>
<body>
    <%
        User user = IEMDBController.getInstance().getActive_user();
        List<Movie> watchlist_movies = new ArrayList<>();
        for(Integer movie_id: user.getWatch())
            watchlist_movies.add(IEMDBController.movieHandler.movies.get(movie_id));
        List<Movie> recommendation_list = user.getRecommendationList();
    %>
    <a href="/">Home</a>
    <p id="email">Email: <%=IEMDBController.getInstance().getActive_user().getEmail()%></p>
    <ul>
        <li id="name">name: <%= user.getName() %></li>
        <li id="nickname">nickname: <%= user.getNickname() %></li>
    </ul>
    <h2>Watch List</h2>
    <table>
        <tr>
            <th>Movie</th>
            <th>releaseDate</th> 
            <th>director</th> 
            <th>genres</th> 
            <th>imdb Rate</th> 
            <th>rating</th> 
            <th>duration</th> 
            <th></th>
            <th></th>
        </tr>
        <%
            for(Movie movie: watchlist_movies) {
        %>
        <tr>
            <th><%= movie.getName() %></th>
            <th><%= movie.getReleaseDate() %></th>
            <th><%= movie.getDirector() %></th>
            <th><%= movie.getGenres() %></th>
            <th><%= movie.getImdbRate() %></th>
            <th><%= movie.getRating() %></th>
            <th>139</th> 
            <td><a href=<%= "/movies/" + new DecimalFormat("00").format(movie.getId()) %>>Link</a></td>
            <td>        
                <form action="" method="POST" >
                    <input id="form_movie_id" type="hidden" name="movie_id" value=<%=new DecimalFormat("00").format(movie.getId())%>>
                    <button type="submit">Remove</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <h2>Recommendation List</h2>
    <table>
        <tr>
            <th>Movie</th>
            <th>imdb Rate</th> 
            <th></th>
        </tr>
        <%
            for(Movie movie: recommendation_list) {
        %>
        <tr>
            <th><%= movie.getName() %>t</th>
            <th><%= movie.getImdbRate() %></th>
            <td><a href=<%= "/movies/" + new DecimalFormat("00").format(movie.getId()) %>>Link</a></td>
        </tr>
        <% } %>
    </table>
</body>
</html>