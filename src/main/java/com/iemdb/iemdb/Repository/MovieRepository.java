package com.iemdb.iemdb.Repository;

import com.iemdb.iemdb.Model.Actor;
import com.iemdb.iemdb.Model.Comment;
import com.iemdb.iemdb.Model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
    List<Movie> findAllByOrderByImdbRateDesc();
    List<Movie> findAllByOrderByReleaseDateDesc();
    List<Movie> findAllByNameContains(String name);
    List<Movie> findAllByGenresContains(String genre);
    List<Movie> findAllByReleaseDateAfter(String date);;
    Movie findAllById(Integer id);
    List<Movie> findAllByCastId(Integer id);
//    List<Comment> findAllByCo(Integer id);
}