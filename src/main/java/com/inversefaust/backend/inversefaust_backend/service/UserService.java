package com.inversefaust.backend.inversefaust_backend.service;

import com.inversefaust.backend.inversefaust_backend.dto.AdviceListResponse;
import com.inversefaust.backend.inversefaust_backend.dto.ScoreResponse;
import com.inversefaust.backend.inversefaust_backend.entity.Advice;
import com.inversefaust.backend.inversefaust_backend.entity.WolfScore;
import com.inversefaust.backend.inversefaust_backend.repository.AdviceRepository;
import com.inversefaust.backend.inversefaust_backend.repository.UserRepository;
import com.inversefaust.backend.inversefaust_backend.repository.WolfScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 이 클래스는 사용자 관련 서비스를 제공합니다.
 *
 *  * @author lavin
 *  * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final WolfScoreRepository wolfScoreRepository;
    private final AdviceRepository adviceRepository;



    /**
     * 이 메서드는 제공된 사용자 ID를 기반으로 최근 5일 동안의 늑대 점수를 조회합니다.
     *
     * @param userId 사용자의 고유 식별자로, 해당 사용자의 늑대 점수를 검색할 때 사용합니다.
     * @return ScoreResponse 객체로, 사용자의 흰색 점수와 검은색 점수를 포함합니다.
     *         해당 사용자의 점수를 찾을 수 없는 경우 RuntimeException("늑대 점수를 조회할 수 없습니다.")이 발생합니다.
     */
    public List<ScoreResponse> getScore(String userId) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime fiveDaysAgo = today.minusDays(4); // 4일 전

        List<WolfScore> wolfScores = wolfScoreRepository.findRecentScores(userId, fiveDaysAgo, today.plusDays(1));

        if (wolfScores.isEmpty()) {
            throw new RuntimeException("늑대 점수를 조회할 수 없습니다.");
        }

        return wolfScores.stream()
                .map(score -> ScoreResponse.builder()
                        .white_score(score.getWhiteScore())
                        .black_score(score.getBlackScore())
                        .dateTime(score.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<AdviceListResponse> getAdviceList(String userId) {
        List<Advice> adviceList = adviceRepository.findByUserId(userId);
        List<AdviceListResponse> response = adviceList.stream()
                .map(advice -> {
                    return AdviceListResponse.builder()
                            .adviceId(advice.getAdviceId())
                            .createdAt(advice.getCreated_at())
                            .adviceContent(advice.getContents())
                            .build();
                })
                .collect(Collectors.toList());

        return response;
    }
}

