package com.inversefaust.backend.inversefaust_backend.service;

import com.inversefaust.backend.inversefaust_backend.dto.AI.AIAdviceRequest;
import com.inversefaust.backend.inversefaust_backend.dto.AIAdviceResponse;
import com.inversefaust.backend.inversefaust_backend.dto.DiaryRequest;
import com.inversefaust.backend.inversefaust_backend.entity.Activity;
import com.inversefaust.backend.inversefaust_backend.entity.Advice;
import com.inversefaust.backend.inversefaust_backend.entity.Diary;
import com.inversefaust.backend.inversefaust_backend.entity.User;
import com.inversefaust.backend.inversefaust_backend.repository.AdviceRepository;
import com.inversefaust.backend.inversefaust_backend.repository.DiaryRepository;
import com.inversefaust.backend.inversefaust_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final AdviceRepository adviceRepository;

    RestTemplate restTemplate = new RestTemplate();
    private final String aiAdviceApiUrl = "http://localhost:8000/api/v2/ai/";

    //일기 저장
    @Transactional
    public String saveDiary(String userId, DiaryRequest diaryRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        diaryRepository.save(Diary.builder()
                .user(user)
                .contents(diaryRequest.getContents())
                .created_at(LocalDateTime.now())
                .build());


//        AIAdviceResponse aiAdvice = getAIAdvice(AIAdviceRequest.builder()
//                .userId(userId)
//                .diary(diaryRequest.getContents())
//                .build());
//
//        log.info("dfsadfadfas " + aiAdvice.getUserId());
//
//        String diaryAdvice = aiAdvice.getDiary_advice();
//
//        adviceRepository.save(Advice.builder()
//                        .user(user)
//                        .created_at(LocalDateTime.now())
//                        .contents(diaryAdvice)
//                        .build());

        return "유튜브는 조금만 운동은 많이 하시는게 좋아요!";
    }

    // 일기 수정
    @Transactional
    public void updateDiary(String diaryId, DiaryRequest diaryRequest) {
        // 기존 일기 조회
        Diary existingDiary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

        // 기존 데이터를 바탕으로 새로운 Diary 객체 생성
        Diary updatedDiary = Diary.builder()
                .diaryId(existingDiary.getDiaryId())  // 기존 diaryId 사용
                .user(existingDiary.getUser())    // 기존 userId 사용
                .contents(diaryRequest.getContents()) // 새로운 내용 설정
                .created_at(existingDiary.getCreated_at()) // 기존 생성일 사용
                .build();

        // 새로운 객체로 덮어쓰기
        diaryRepository.save(updatedDiary);
    }

    // 일기 삭제
    @Transactional
    public void deleteDiary(String diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

        diaryRepository.delete(diary);
    }


    public AIAdviceResponse getAIAdvice(AIAdviceRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // RequestBody 설정
        HttpEntity<AIAdviceRequest> requestEntity = new HttpEntity<>(request, headers);

        // POST 요청 보내기
        ResponseEntity<AIAdviceResponse> responseEntity = restTemplate.exchange(
                "http://localhost:8000/api/v2/ai/",
                HttpMethod.POST,
                requestEntity,
                AIAdviceResponse.class
        );

        // Response 반환
        return responseEntity.getBody();
    }


}
