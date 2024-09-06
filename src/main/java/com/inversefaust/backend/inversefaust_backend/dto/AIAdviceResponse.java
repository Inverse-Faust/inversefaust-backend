package com.inversefaust.backend.inversefaust_backend.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AIAdviceResponse {

    private String userId;
    private String diary_sentiment;
    private int diary_score;
    private String diary_advice;
}