package com.inversefaust.backend.inversefaust_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Diary")
public class Diary {

    @Id
    @Column(name = "diary_id", length = 255)
    private String diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "contents", nullable = false, length = 2000)
    @NotNull
    private String contents;

    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull
    private LocalDateTime created_at;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diary diary = (Diary) o;
        return Objects.equals(diaryId, diary.diaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaryId);
    }
}