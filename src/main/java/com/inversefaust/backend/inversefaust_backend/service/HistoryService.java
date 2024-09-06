package com.inversefaust.backend.inversefaust_backend.service;

import com.inversefaust.backend.inversefaust_backend.dto.HistoryResponse;
import com.inversefaust.backend.inversefaust_backend.entity.Diary;
import com.inversefaust.backend.inversefaust_backend.entity.UserActivity;
import com.inversefaust.backend.inversefaust_backend.repository.DiaryRepository;
import com.inversefaust.backend.inversefaust_backend.repository.UserActivityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final DiaryRepository diaryRepository;
    private final UserActivityRepository userActivityRepository;

    public List<HistoryResponse> getHistory(String userId) {
        // 사용자 활동 기록과 일기 기록 가져오기
        List<UserActivity> userActivityList = userActivityRepository.findByUserId(userId);
        List<Diary> userDiaryList = diaryRepository.findByUserId(userId);

        // 활동 기록을 날짜별로 그룹화
        Map<LocalDate, List<UserActivity>> activityByDate = userActivityList.stream()
                .collect(Collectors.groupingBy(activity -> activity.getCreatedAt().toLocalDate()));

        // 일기를 날짜별로 맵핑 (키 중복 시 기존 값을 유지하거나 덮어쓰기)
        Map<LocalDate, String> diaryByDate = userDiaryList.stream()
                .collect(Collectors.toMap(
                        diary -> diary.getCreated_at().toLocalDate(),
                        Diary::getContents,
                        (existing, replacement) -> existing // 기존 값을 유지하도록 설정
                ));
        // HistoryResponse 리스트 생성
        List<HistoryResponse> historyResponses = new ArrayList<>();

        for (Map.Entry<LocalDate, List<UserActivity>> entry : activityByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<UserActivity> activities = entry.getValue();

            // 활동 목록을 맵으로 변환 (Activity 이름을 키로, 활동 시간을 값으로 설정)
            Map<String, Integer> activityList = activities.stream()
                    .collect(Collectors.toMap(activity -> activity.getActivity().getActivityName(), UserActivity::getActivityDuration));

            // 해당 날짜의 일기를 가져오기 (없을 수도 있음)
            String diaryContent = diaryByDate.getOrDefault(date, "");

            // 빌더 패턴을 사용하여 HistoryResponse 객체 생성
            HistoryResponse historyResponse = HistoryResponse.builder()
                    .date(date.atStartOfDay())  // LocalDate를 LocalDateTime으로 변환
                    .activityList(activityList)  // 활동 리스트 설정
                    .diaryContent(diaryContent)  // 일기 내용 설정
                    .build();

            // 리스트에 추가
            historyResponses.add(historyResponse);
        }

        return historyResponses;
    }
}
