package com.sashank.skillswap.service;

import com.sashank.skillswap.dto.request.CreateRatingRequest;
import com.sashank.skillswap.dto.response.RatingResponse;
import com.sashank.skillswap.dto.response.RatingSummaryResponse;
import java.util.List;

public interface RatingService {
    RatingResponse rateSession(Long userId, Long sessionId, CreateRatingRequest request);
    List<RatingResponse> getUserRatings(Long userId);
    RatingSummaryResponse getRatingSummary(Long userId);
    List<RatingResponse> getMyReceivedRatings(Long userId);
}

