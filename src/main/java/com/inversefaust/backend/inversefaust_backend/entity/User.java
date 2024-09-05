package com.inversefaust.backend.inversefaust_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User {

    @Id
    @Column(name = "user_id", length = 255)
    private String userId;

    @Column(name = "password", nullable = false, length = 255)
    @NotNull
    private String password;

    @Column(name = "name", nullable = false, length = 255)
    @NotNull
    private String name;

    @Column(name = "white_score", nullable = false)
    @NotNull
    private int white_score;

    @Column(name = "black_score", nullable = false)
    @NotNull
    private int black_score;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}