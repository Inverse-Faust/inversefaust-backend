package com.inversefaust.backend.inversefaust_backend.repository;

import com.inversefaust.backend.inversefaust_backend.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, String> {
}
