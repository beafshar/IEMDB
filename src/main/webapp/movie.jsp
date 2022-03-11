<%@ page import="com.myservlet.Model.IEMDBController" %>
<%@ page import="com.myservlet.Model.Movie" %>
<%@ page import="com.myservlet.Model.Actor" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Movie</title>
    <style>
      li, td, th {
        padding: 5px;
      }
    </style>
  </head>
  <body>
    <%
      String movie_id = (String)request.getAttribute("movie_id");
      Movie movie = IEMDBController.movieHandler.movies.get(Integer.parseInt(movie_id));
      List<Actor> actors = new ArrayList<>();
      for(Integer actor_id: movie.getCast()) {
        actors.add(IEMDBController.actorHandler.actors.get(actor_id));
      }
    %>
    <a href="/">Home</a>
    <p id="email">Email: <%=IEMDBController.getInstance().getActive_user().getEmail()%></p>
    <ul>
      <li id="name">name:  <%= movie.getName() %></li>
      <li id="summary">
        summary: <%= movie.getSummary() %>
      </li>
      <li id="releaseDate">releaseDate: <%= movie.getReleaseDate() %></li>
      <li id="director">director: <%= movie.getDirector() %></li>
      <li id="writers">writers: <%= movie.getWriters() %></li>
      <li id="genres">genres: <%= movie.getGenres() %></li>
      <li id="imdbRate">imdb Rate: <%= movie.getImdbRate() %></li>
      <li id="rating">rating: <%= movie.getRating() %></li>
      <li id="duration">duration: <%= movie.getDuration() %> minutes</li>
      <li id="ageLimit">ageLimit: <%= movie.getAgeLimit() %></li>
    </ul>
    <h3>Cast</h3>
    <table>
      <tr>
        <th>name</th>
        <th>age</th>
      </tr>
      <%
        for(Actor actor  : actors){ %>
      <tr>
        <td><%= actor.getName() %></td>
        <td><%= actor.getAge() %></td>
        <td><a href=<%= "/actors/" + new DecimalFormat("00").format(actor.getId()) %>>Link</a></td>
      </tr>
      <% } %>
    </table>
    <br>
    <form action="" method="POST">
      <label>Rate(between 1 and 10):</label>
      <input type="number" id="quantity" name="quantity" min="1" max="10">
      <input type="hidden" id="rate_action" name="action" value="rate">
      <input type="hidden" id="rate_movie_id" name="movie_id" value=<%=movie_id%>>
      <button type="submit">rate</button>
    </form>
    <br>
    <form action="" method="POST">
      <input type="hidden" id="watchlist_action" name="action" value="add">
      <input type="hidden" id="watchlist_movie_id" name="movie_id" value="01">
      <button type="submit">Add to WatchList</button>
    </form>
    <br />
    <table>
      <tr>
        <th>nickname</th>
        <th>comment</th>
        <th></th>
        <th></th>
      </tr>
      <tr>
        <td>@sara</td>
        <td>Nice</td>
        <td>
          <form action="" method="POST">
           <label>3</label>
            <input id="like_comment_id" type="hidden" name="comment_id" value="03"/>
            <input type="hidden" id="like_action" name="action" value="like">
            <input type="hidden" id="like_movie_id" name="movie_id" value="01">
            <button type="submit">like</button>
          </form>
        </td>
        <td>
          <form action="" method="POST">
            <label>1</label>
            <input id="form_comment_id" type="hidden" name="comment_id" value="03"/>
            <input type="hidden" id="form_action" name="action" value="dislike">
            <input type="hidden" id="form_movie_id" name="movie_id" value="01">
            <button type="submit">dislike</button>
          </form>
        </td>
      </tr>
    </table>
    <br><br>
    <form action="" method="POST">
      <label>Your Comment:</label>
      <input type="text" name="comment" value="">
      <input type="hidden" id="comment_action" name="action" value="comment">
      <input type="hidden" id="comment_movie_id" name="movie_id" value="01">
      <button type="submit">Add Comment</button>
    </form>
  </body>
</html>
