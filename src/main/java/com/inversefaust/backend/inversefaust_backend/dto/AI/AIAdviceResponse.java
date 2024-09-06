package com.inversefaust.backend.inversefaust_backend.dto.AI;


import lombok.*;

// AIAdviceResponse.java
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIAdviceResponse {
    private String userId;
    private String diarySentiment;
    private double diaryScore;
    private String diaryAdvice;
}