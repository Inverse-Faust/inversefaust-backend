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
import java.util.*;
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

        // 일기를 날짜별로 그룹화한 후 각 날짜별로 가장 최신의 일기만 선택
        Map<LocalDate, String> diaryByDate = userDiaryList.stream()
                .collect(Collectors.groupingBy(
                        diary -> diary.getCreated_at().toLocalDate(),
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Diary::getCreated_at)),
                                optionalDiary -> optionalDiary.map(Diary::getContents).orElse("")
                        )
                ));

        // HistoryResponse 리스트 생성
        List<HistoryResponse> historyResponses = new ArrayList<>();

        // 모든 날짜에 대해 처리하기 위해 활동 날짜와 일기 날짜의 전체 날짜 목록 구하기
        Set<LocalDate> allDates = new HashSet<>(activityByDate.keySet());
        allDates.addAll(diaryByDate.keySet());

        for (LocalDate date : allDates) {
            // 해당 날짜의 활동 목록 가져오기 (없으면 빈 리스트)
            List<UserActivity> activities = activityByDate.getOrDefault(date, new ArrayList<>());

            // 활동 목록을 맵으로 변환 (Activity 이름을 키로, 활동 시간을 값으로 설정)
            Map<String, Integer> activityList = activities.stream()
                    .collect(Collectors.toMap(activity -> activity.getActivity().getActivityName(), UserActivity::getActivityDuration));

            // 해당 날짜의 가장 최신 일기 가져오기 (없으면 빈 문자열)
            String diaryContent = diaryByDate.getOrDefault(date, "");

            // 조건에 따른 일기 내용 설정
            if (diaryContent.isEmpty() && activityList.isEmpty()) {
                diaryContent = "작성하신 일기가 없습니다";
            }

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
