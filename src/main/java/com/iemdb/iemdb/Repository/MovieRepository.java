package com.iemdb.iemdb.Repository;

import com.iemdb.iemdb.Model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
}