package com.inversefaust.backend.inversefaust_backend.repository;

import com.inversefaust.backend.inversefaust_backend.entity.UserActivity;
import com.inversefaust.backend.inversefaust_backend.entity.idClass.UserActivityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, UserActivityId> {

    @Query("SELECT ua FROM UserActivity ua WHERE ua.user.userId = :userId")
    List<UserActivity> findByUserId(@Param("userId") String userId);

    // 특정 사용자에 대한 최근 5개의 활동을 가져오기
    @Query("SELECT ua FROM UserActivity ua WHERE ua.user.userId = :userId ORDER BY ua.createdAt DESC")
    List<UserActivity> findTop5RecentByUserId(@Param("userId") String userId);
}