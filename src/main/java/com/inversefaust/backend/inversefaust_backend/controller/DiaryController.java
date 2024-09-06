package com.inversefaust.backend.inversefaust_backend.controller;

import com.inversefaust.backend.inversefaust_backend.dto.DiaryRequest;
import com.inversefaust.backend.inversefaust_backend.dto.DiarySaveResponse;
import com.inversefaust.backend.inversefaust_backend.dto.ScoreResponse;
import com.inversefaust.backend.inversefaust_backend.service.DiaryService;
import com.inversefaust.backend.inversefaust_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {

    private final DiaryService diaryService;

    //일기 저장
    @PostMapping("/{userId}")
    public ResponseEntity<DiarySaveResponse> getScore(@PathVariable("userId") String userId, @RequestBody @Validated DiaryRequest diaryRequest){
       diaryService.saveDiary(userId, diaryRequest);
        DiarySaveResponse response =
                DiarySaveResponse.builder()
                        .white_score(10)
                        .black_score(100)
                        .advice("유튜브는 조금만 보세요!")
                        .build();
        return ResponseEntity.ok(response);
    }

    // 일기 수정
    @PutMapping("/{diaryId}")
    public ResponseEntity<String> updateDiary(@PathVariable("diaryId") String diaryId, @RequestBody @Validated DiaryRequest diaryRequest) {
        diaryService.updateDiary(diaryId, diaryRequest);
        return ResponseEntity.ok("일기가 성공적으로 수정되었습니다.");
    }

    // 일기 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<String> deleteDiary(@PathVariable("diaryId") String diaryId) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.ok("일기가 성공적으로 삭제되었습니다.");
    }




}