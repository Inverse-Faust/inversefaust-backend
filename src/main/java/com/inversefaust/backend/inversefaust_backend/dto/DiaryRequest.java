package com.inversefaust.backend.inversefaust_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일기 저장 요청 dto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRequest {
    String contents;
}
