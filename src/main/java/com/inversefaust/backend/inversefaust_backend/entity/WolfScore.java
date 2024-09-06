package com.inversefaust.backend.inversefaust_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WolfScore")
public class WolfScore {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "score_id", nullable = false, length = 255)
    private String scoreId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "white_score", nullable = false)
    @NotNull
    private int whiteScore;

    @Column(name = "black_score", nullable = false)
    @NotNull
    private int blackScore;

    @Column(name = "created_at", nullable = false)
    @NotNull
    private LocalDateTime createdAt;


}