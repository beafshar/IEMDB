package com.iemdb.iemdb.Repository;

import com.iemdb.iemdb.Model.Comment;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Comment findAllById(Integer id);
}