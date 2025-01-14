package com.inversefaust.backend.inversefaust_backend.entity.idClass;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "activity_id")
    private String activityId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserActivityId that = (UserActivityId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(activityId, that.activityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, activityId);
    }
}