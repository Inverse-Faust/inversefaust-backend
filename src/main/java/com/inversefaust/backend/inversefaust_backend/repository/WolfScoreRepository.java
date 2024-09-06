package com.inversefaust.backend.inversefaust_backend.repository;

import com.inversefaust.backend.inversefaust_backend.entity.Diary;
import com.inversefaust.backend.inversefaust_backend.entity.WolfScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WolfScoreRepository extends JpaRepository<WolfScore, String> {

    @Query("SELECT ws FROM WolfScore ws WHERE ws.user.userId = :userId AND ws.createdAt BETWEEN :startDate AND :endDate")
    List<WolfScore> findRecentScores(@Param("userId") String userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}