package com.inversefaust.backend.inversefaust_backend.dto.AI;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIScoreResponse {
    private String userId;
    private int whiteScore;
    private int blackScore;
}