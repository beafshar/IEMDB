package com.iemdb.iemdb.Repository;

import com.iemdb.iemdb.Model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}