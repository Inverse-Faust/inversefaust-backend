package com.inversefaust.backend.inversefaust_backend.repository;

import com.inversefaust.backend.inversefaust_backend.entity.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdviceRepository extends JpaRepository<Advice, String> {

    @Query("SELECT a FROM Advice a WHERE a.user.userId = :userId")
    List<Advice> findByUserId(@Param("userId") String userId);

}
