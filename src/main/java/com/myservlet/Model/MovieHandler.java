package com.myservlet.Model;

import com.myservlet.Model.Error.MovieNotFound;
import java.time.LocalDate;
import java.util.*;

public class MovieHandler {
    public static Map<Integer, Movie> movies = new HashMap<>();

    public void setMovies(Movie[] movies) {
        for (Movie movie : movies)
            this.movies.put(movie.getId(), movie);
    }

    public static Movie findMovie(int movie_id) throws MovieNotFound {
        if (movies.containsKey(movie_id))
            return movies.get(movie_id);
        throw new MovieNotFound();
    }

    public static List<Movie> getMovieByYear(int start_year, int end_year) {
        List<Movie> mov = new ArrayList<>();
        for (Movie movie : movies.values()) {
            LocalDate movie_year = LocalDate.parse(movie.getReleaseDate());
            if (movie_year.getYear() >= start_year && movie_year.getYear() <= end_year)
                mov.add(movie);
        }
        return mov;
    }

    public static List<Movie> filterMovies(String filter, List<Movie> movies) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies)
            if (movie.getName().toLowerCase().contains(filter.toLowerCase()))
                filteredMovies.add(movie);
        return filteredMovies;
    }

    public static List<Movie> getMovies(String filter, String sort_imdb, String sort_date) {
        List <Movie> movies_list = new ArrayList<>(movies.values());
        if (filter != null) movies_list = filterMovies(filter, movies_list);
        if (sort_imdb.equals("1")) movies_list.sort(Comparator.comparing(Movie::getImdbRate).reversed());
        if (sort_date.equals("1")) movies_list.sort(Comparator.comparing(Movie::getReleaseDate).reversed());
        return movies_list;
    }

}
