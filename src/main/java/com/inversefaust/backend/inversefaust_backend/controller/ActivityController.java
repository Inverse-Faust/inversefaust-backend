package com.inversefaust.backend.inversefaust_backend.controller;

import com.inversefaust.backend.inversefaust_backend.dto.ActivityListResponse;
import com.inversefaust.backend.inversefaust_backend.dto.NewActivityResponse;
import com.inversefaust.backend.inversefaust_backend.dto.RecentActivityListResponse;
import com.inversefaust.backend.inversefaust_backend.dto.UserActivityRequest;
import com.inversefaust.backend.inversefaust_backend.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activity")
public class ActivityController {


    private final ActivityService activityService;


    //전체 활동 리스트 조회
    @GetMapping
    public ResponseEntity<List<ActivityListResponse>> getActivityList(){
        List<ActivityListResponse> activityList = activityService.getActivityList();
        return ResponseEntity.ok(activityList);
    }

    //오늘 활동 기록 저장 요청
    @PostMapping("/{userId}")
    public ResponseEntity<String> saveTodayActivity(@RequestBody List<UserActivityRequest> activityList, @PathVariable("userId") String userId){
        activityService.saveTodayActivity(activityList, userId);
        return ResponseEntity.ok("오늘 활동을 기록했습니다.");
    }

    //새로운 행동 추가 요청
    // 활동 추가 요청
    @PostMapping
    public ResponseEntity<String> addActivity(@RequestBody @Validated NewActivityResponse activityRequest) {
        activityService.addActivity(activityRequest);
        return ResponseEntity.ok("활동이 성공적으로 추가되었습니다.");
    }

    // 최근 기록된 활동 최대 5개 조회
    @GetMapping("/recent/{userId}")
    public ResponseEntity<List<RecentActivityListResponse>> getRecentActivities(@PathVariable String userId) {
        List<RecentActivityListResponse> recentActivities = activityService.getRecentActivities(userId);
        return ResponseEntity.ok(recentActivities);
    }
}
