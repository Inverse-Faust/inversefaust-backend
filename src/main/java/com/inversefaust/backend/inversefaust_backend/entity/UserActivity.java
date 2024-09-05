package com.inversefaust.backend.inversefaust_backend.entity;

import com.inversefaust.backend.inversefaust_backend.entity.Activity;
import com.inversefaust.backend.inversefaust_backend.entity.idClass.UserActivityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserActivity")
public class UserActivity {

    @EmbeddedId
    private UserActivityId userActivityId = new UserActivityId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("activityId")
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "activity_duration", nullable = false)
    private int activityDuration;

}
