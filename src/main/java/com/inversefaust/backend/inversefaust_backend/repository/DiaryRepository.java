package com.inversefaust.backend.inversefaust_backend.repository;

import com.inversefaust.backend.inversefaust_backend.entity.Diary;
import com.inversefaust.backend.inversefaust_backend.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, String> {

    @Query("SELECT d FROM Diary d WHERE d.user.userId = :userId")
    List<Diary> findByUserId(@Param("userId") String userId);

}
