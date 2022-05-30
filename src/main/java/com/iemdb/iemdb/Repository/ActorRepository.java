package com.iemdb.iemdb.Repository;

import com.iemdb.iemdb.Model.Actor;
import com.iemdb.iemdb.Model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends CrudRepository<Actor, Integer> {
    Actor findAllById(Integer id);
    List<Actor> findAllByMovies(Movie movie);


}