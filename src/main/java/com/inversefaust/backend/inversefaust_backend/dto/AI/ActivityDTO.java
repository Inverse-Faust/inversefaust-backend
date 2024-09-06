package com.inversefaust.backend.inversefaust_backend.dto.AI;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {
    private String activity;
    private int duration;
}
