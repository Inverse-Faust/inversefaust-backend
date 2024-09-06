package com.inversefaust.backend.inversefaust_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 흰 늑대, 검은 늑대 점수 반환 dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreResponse {
    private int white_score;
    private int black_score;
    LocalDateTime dateTime;
}
