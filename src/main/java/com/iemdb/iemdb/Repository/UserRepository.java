package com.iemdb.iemdb.Repository;

import com.iemdb.iemdb.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    @Query(value = "select user from User user where (user.email = ?1) and (user.password = ?2)")
    List<User> findByUsernameAndPassword(String username, String password);
    User findByEmail(String email);
}