package com.inversefaust.backend.inversefaust_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdviceListResponse  {
    String adviceId;
    LocalDateTime createdAt;
    String adviceContent;


}
