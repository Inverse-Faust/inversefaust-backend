package com.inversefaust.backend.inversefaust_backend.dto.AI;

import lombok.*;

// AIAdviceRequest.java
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIAdviceRequest {
    private String userId;
    private String diary;
}
