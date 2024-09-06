package com.inversefaust.backend.inversefaust_backend.controller;

import com.inversefaust.backend.inversefaust_backend.dto.HistoryResponse;
import com.inversefaust.backend.inversefaust_backend.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<HistoryResponse>> getHistory(@PathVariable("userId") String userId) {
        List<HistoryResponse> history = historyService.getHistory(userId);
        return ResponseEntity.ok(history);
    }
}
