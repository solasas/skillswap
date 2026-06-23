package com.sashank.skillswap.service;

import com.sashank.skillswap.dto.response.MatchResponse;
import java.util.List;

public interface MatchService {
    List<MatchResponse> findMatches(Long userId);
    List<MatchResponse> findMutualMatches(Long userId);
    MatchResponse checkMatch(Long userId, Long otherUserId);
}

