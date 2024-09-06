package com.inversefaust.backend.inversefaust_backend.dto;

import lombok.Builder;

import java.util.List;
@Builder
public class AIAdviceRequest {

    private String userId;
    private List<ActivityDTO> activities;
    private String diary;

    // Getter & Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ActivityDTO> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityDTO> activities) {
        this.activities = activities;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    // ActivityDTO 내부 클래스
    public static class ActivityDTO {
        private String activity;
        private int duration;

        // Getter & Setter
        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }
}