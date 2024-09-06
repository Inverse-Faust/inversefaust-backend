package com.inversefaust.backend.inversefaust_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일기 저장 후 응답 DTP
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiarySaveResponse {
    private int white_score;
    private int black_score;
    private  String advice;
}