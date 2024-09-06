package com.inversefaust.backend.inversefaust_backend.repository;

import com.inversefaust.backend.inversefaust_backend.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository extends JpaRepository<Activity, String> {

    @Query("SELECT a FROM Activity a WHERE a.activityId = :activityId")
    Activity findByActivityId(@Param("activityId") String activityId);
}
