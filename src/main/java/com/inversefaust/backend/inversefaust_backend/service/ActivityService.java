package com.inversefaust.backend.inversefaust_backend.service;

import com.inversefaust.backend.inversefaust_backend.dto.ActivityListResponse;
import com.inversefaust.backend.inversefaust_backend.dto.NewActivityResponse;
import com.inversefaust.backend.inversefaust_backend.dto.RecentActivityListResponse;
import com.inversefaust.backend.inversefaust_backend.dto.UserActivityRequest;
import com.inversefaust.backend.inversefaust_backend.entity.Activity;
import com.inversefaust.backend.inversefaust_backend.entity.User;
import com.inversefaust.backend.inversefaust_backend.entity.UserActivity;
import com.inversefaust.backend.inversefaust_backend.entity.idClass.UserActivityId;
import com.inversefaust.backend.inversefaust_backend.repository.ActivityRepository;
import com.inversefaust.backend.inversefaust_backend.repository.UserActivityRepository;
import com.inversefaust.backend.inversefaust_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final UserActivityRepository userActivityRepository;

    public List<ActivityListResponse> getActivityList() {
        return activityRepository.findAll()
                .stream()
                .map(this::buildActivityListResponse)
                .collect(Collectors.toList());
    }


    @Transactional
    public void saveTodayActivity(List<UserActivityRequest> activityList, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<UserActivity> userActivities = activityList.stream()
                .map(activityRequest -> {
                    Activity activity = activityRepository.findById(activityRequest.getActivityId())
                            .orElseThrow(() -> new IllegalArgumentException("Activity not found"));

                    // 복합 키 생성 및 설정
                    UserActivityId userActivityId = new UserActivityId(userId, activityRequest.getActivityId());

                    return UserActivity.builder()
                            .userActivityId(userActivityId)  // 복합 키 설정
                            .user(user)
                            .activity(activity)
                            .createdAt(LocalDateTime.now())
                            .activityDuration(activityRequest.getActivityDuration())
                            .build();
                })
                .collect(Collectors.toList());

        userActivityRepository.saveAll(userActivities);
    }


    private ActivityListResponse buildActivityListResponse(Activity activity) {
        return ActivityListResponse.builder()
                .activityId(activity.getActivityId())
                .activityName(activity.getActivityName())
                .purpose(activity.getPurpose())
                .build();
    }

    @Transactional
    public void addActivity(NewActivityResponse activityRequest) {
        Activity activity = Activity.builder()
                .activityName(activityRequest.getActivityName())
                .purpose(activityRequest.getPurpose())
                .build();
        activityRepository.save(activity);
    }

    // 최근 기록된 활동 최대 5개 조회
    @Transactional
    public List<RecentActivityListResponse> getRecentActivities(String userId) {
        List<UserActivity> recentActivities = userActivityRepository.findTop5RecentByUserId(userId);

        return recentActivities.stream()
                .map(this::convertToActivityResponse)
                .collect(Collectors.toList());
    }

    private RecentActivityListResponse convertToActivityResponse(UserActivity userActivity) {
        return RecentActivityListResponse.builder()
                .activityId(userActivity.getActivity().getActivityId())
                .activityName(userActivity.getActivity().getActivityName())
                .activityDuration(userActivity.getActivityDuration())
                .createdAt(userActivity.getCreatedAt())
                .build();
    }
}
