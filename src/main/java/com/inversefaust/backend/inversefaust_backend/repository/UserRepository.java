package com.inversefaust.backend.inversefaust_backend.repository;

import com.inversefaust.backend.inversefaust_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

}