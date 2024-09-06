package com.inversefaust.backend.inversefaust_backend.dto.AI;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIScoreRequest {
    private String userId;
    private List<ActivityDTO> activities;
}