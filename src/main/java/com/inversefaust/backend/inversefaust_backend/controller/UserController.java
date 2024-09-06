package com.inversefaust.backend.inversefaust_backend.controller;

import com.inversefaust.backend.inversefaust_backend.dto.AdviceListResponse;
import com.inversefaust.backend.inversefaust_backend.dto.ScoreResponse;
import com.inversefaust.backend.inversefaust_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 이 컨트롤러는 사용자 관련 API를 처리하는 컨트롤러입니다.
 *
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 이 메서드는 제공된 사용자 ID를 기반으로 사용자의 늑대 점수를 조회합니다.
     *
     * @param userId 사용자의 고유 식별자로, 해당 사용자의 점수를 검색할 때 사용합니다.
     * @return ResponseEntity로, ScoreResponse 객체를 포함합니다.
     *         이 객체는 사용자의 흰 늑대, 검은 늑대 점수를 담고 있습니다.
     *         응답의 HTTP 상태 코드는 HTTP.OK
     */
    @GetMapping("/score/{userId}")
    public ResponseEntity<ScoreResponse> getScore(@PathVariable("userId") String userId){
        ScoreResponse response = userService.getScore(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/advice/{userId}")
    public ResponseEntity<List<AdviceListResponse>> getAdviceList(@PathVariable("userId") String userId){
        List<AdviceListResponse> adviceList = userService.getAdviceList(userId);
        return ResponseEntity.ok(adviceList);
    }

}