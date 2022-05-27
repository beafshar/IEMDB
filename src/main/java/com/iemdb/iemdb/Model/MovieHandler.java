package com.iemdb.iemdb.Model;

import com.iemdb.iemdb.Model.Error.MovieNotFound;

import java.time.LocalDate;
import java.util.*;

public class MovieHandler {
    public static Map<Integer, Movie> movies = new HashMap<>();

    public void setMovies(Movie[] movies) {
        for (Movie movie : movies)
            this.movies.put(movie.getId(), movie);
    }

    public ArrayList<Movie> getMovies() {
        return new ArrayList<>(movies.values());
    }

    public static Movie findMovie(int movie_id) throws MovieNotFound {
        if (movies.containsKey(movie_id))
            return movies.get(movie_id);
        else throw new MovieNotFound();
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

    public static ArrayList<Movie> getMovies(String filter_name, String filter_genre, String sort_imdb, String sort_date) {
        List <Movie> movies_list = new ArrayList<>(movies.values());
        if (filter_name != null) movies_list = filterMovies(filter_name, movies_list);
//        System.out.println(filter_genre);
        if (filter_genre != null) movies_list = getMovieByGenre(filter_genre, movies_list);
        if (sort_imdb != null) movies_list.sort(Comparator.comparing(Movie::getImdbRate).reversed());
        if (sort_date != null) movies_list.sort(Comparator.comparing(Movie::getReleaseDate).reversed());
        return (ArrayList<Movie>) movies_list;
    }

    public static ArrayList<Movie> getMovieByGenre(String filter, List<Movie> prev_movies) {
        List <Movie> movies_list = new ArrayList<>();
        for (Movie movie : prev_movies)
            for (String genre : movie.getGenres())
                if (genre.equalsIgnoreCase(filter))
                    movies_list.add(movie);
        return (ArrayList<Movie>) movies_list;
    }

}
