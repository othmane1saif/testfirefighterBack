package com.example.test.technique.repository;

import com.example.test.technique.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
//
    @Query(value = "select u from User u left join Leave l on l.user.id = u.leave.user.id and ?2 not between l.dateDepart and l.dateFin where u.team = ?1 order by u.name asc ")
    List<User> findAllByTeam(String team, LocalDate date);
}
