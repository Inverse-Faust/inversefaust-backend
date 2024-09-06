package com.inversefaust.backend.inversefaust_backend.service;

import com.inversefaust.backend.inversefaust_backend.dto.AdviceListResponse;
import com.inversefaust.backend.inversefaust_backend.dto.ScoreResponse;
import com.inversefaust.backend.inversefaust_backend.entity.Advice;
import com.inversefaust.backend.inversefaust_backend.repository.AdviceRepository;
import com.inversefaust.backend.inversefaust_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final UserRepository userRepository;
    private final AdviceRepository adviceRepository;


    /**
     * 이 메서드는 제공된 사용자 ID를 기반으로 사용자의 늑대 점수를 조회 합니다.
     *
     * @param userId 사용자의 고유 식별자로, 해당 사용자의 점수를 검색할 때 사용합니다.
     * @return ScoreResponse 객체로, 사용자의 흰색 점수와 검은색 점수를 포함합니다.
     *         사용자를 찾을 수 없는 경우 RuntimeException("사용자를 조회할 수 없습니다.")이 발생합니다.
     */
    public ScoreResponse getScore(String userId) {
        return userRepository.findById(userId)
                .map(user -> ScoreResponse.builder()
                        .white_score(user.getWhite_score())
                        .black_score(user.getBlack_score())
                        .build())
                .orElseThrow(() -> new RuntimeException("사용자를 조회할 수 없습니다."));
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

