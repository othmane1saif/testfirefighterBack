package com.example.test.technique.repository;

import com.example.test.technique.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query(value = "select l from Leave l where l.user.id = ?1 and current_date between l.dateDepart and l.dateFin")
    Optional<Leave> findByUser(Long user_id);

    @Query(value = "select l from Leave l where current_date <= l.dateFin")
    List findAllActiveLeaves();
}
