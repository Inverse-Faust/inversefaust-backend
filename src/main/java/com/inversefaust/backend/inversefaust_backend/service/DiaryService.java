package com.inversefaust.backend.inversefaust_backend.service;

import com.inversefaust.backend.inversefaust_backend.dto.DiaryRequest;
import com.inversefaust.backend.inversefaust_backend.entity.Diary;
import com.inversefaust.backend.inversefaust_backend.entity.User;
import com.inversefaust.backend.inversefaust_backend.repository.DiaryRepository;
import com.inversefaust.backend.inversefaust_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    //일기 저장
    @Transactional
    public void saveDiary(String userId, DiaryRequest diaryRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        diaryRepository.save(Diary.builder()
                .user(user)
                .contents(diaryRequest.getContents())
                .created_at(LocalDateTime.now())
                .build());
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




}
