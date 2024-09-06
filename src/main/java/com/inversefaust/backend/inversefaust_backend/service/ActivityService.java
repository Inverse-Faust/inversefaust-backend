package com.inversefaust.backend.inversefaust_backend.service;

import com.inversefaust.backend.inversefaust_backend.dto.*;
import com.inversefaust.backend.inversefaust_backend.dto.AI.AIAdviceRequest;
import com.inversefaust.backend.inversefaust_backend.dto.AI.AIScoreRequest;
import com.inversefaust.backend.inversefaust_backend.dto.AI.AIScoreResponse;
import com.inversefaust.backend.inversefaust_backend.dto.AI.ActivityDTO;
import com.inversefaust.backend.inversefaust_backend.entity.Activity;
import com.inversefaust.backend.inversefaust_backend.entity.User;
import com.inversefaust.backend.inversefaust_backend.entity.UserActivity;
import com.inversefaust.backend.inversefaust_backend.entity.WolfScore;
import com.inversefaust.backend.inversefaust_backend.entity.idClass.UserActivityId;
import com.inversefaust.backend.inversefaust_backend.repository.ActivityRepository;
import com.inversefaust.backend.inversefaust_backend.repository.UserActivityRepository;
import com.inversefaust.backend.inversefaust_backend.repository.UserRepository;
import com.inversefaust.backend.inversefaust_backend.repository.WolfScoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final UserActivityRepository userActivityRepository;
    private final WolfScoreRepository wolfScoreRepository;


    RestTemplate restTemplate = new RestTemplate();
    private final String aiActivityApiUrl = "http://localhost:8000/api/v1/ai/";



    public List<ActivityListResponse> getActivityList() {
        return activityRepository.findAll()
                .stream()
                .map(this::buildActivityListResponse)
                .collect(Collectors.toList());
    }


    //오늘 활동 저장
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

        // UserActivityRequest 리스트를 ActivityDTO 리스트로 변환

        List<ActivityDTO> activityDTOList = userActivities.stream()
                .map(userActivity -> {
                    Activity activity = activityRepository.findByActivityId(userActivity.getUserActivityId().getActivityId());

                    return ActivityDTO.builder()
                            .activity(activity.getActivityName() + " " + activity.getPurpose())
                           .duration(userActivity.getActivityDuration())
                           .build();
                })
                .collect(Collectors.toList());

        //AI 응답 요청
        AIScoreResponse aiActivity = getAIActivity(AIScoreRequest.builder()
                .userId(userId)
                .activities(activityDTOList)
                .build());


        log.info("Activity: " + aiActivity);



        // WolfScore 저장
        wolfScoreRepository.save(WolfScore.builder()
                .user(user)
                .whiteScore(aiActivity.getWhiteScore())
                .blackScore(aiActivity.getBlackScore())
                .createdAt(LocalDateTime.now())
                .build());

        // UserActivity 저장
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


    private AIScoreResponse getAIActivity(AIScoreRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // RequestBody 설정
        HttpEntity<AIScoreRequest> requestEntity = new HttpEntity<>(request, headers);

        // POST 요청 보내기
        ResponseEntity<AIScoreResponse> responseEntity = restTemplate.exchange(
                "http://localhost:8000/api/v1/ai",
                HttpMethod.POST,
                requestEntity,
                AIScoreResponse.class
        );

        // Response 반환
        return responseEntity.getBody();
    }
}
