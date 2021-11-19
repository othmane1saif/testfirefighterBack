package com.example.test.technique.repository;

import com.example.test.technique.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);

    @Query(value = "select * from user where team like '%:team%' order by name desc", nativeQuery = true)
    List<User> findAllByTeam(@Param("team") String team);
}
