package com.inversefaust.backend.inversefaust_backend.dto;

import com.inversefaust.backend.inversefaust_backend.entity.Activity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponse {
    private LocalDateTime date;
    private Map<String, Integer> activityList;
    private String diaryContent;
}
