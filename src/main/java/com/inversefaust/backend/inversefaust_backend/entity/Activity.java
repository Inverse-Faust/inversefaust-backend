package com.inversefaust.backend.inversefaust_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "activity_id", length = 255)
    private String activityId;

    @Column(name = "activity_name", nullable = false, length = 1000)
    @NotNull
    private String activityName;

    @Column(name = "purpose", nullable = false, length = 255)
    @NotNull
    private String purpose;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(activityId, activity.activityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId);
    }
}