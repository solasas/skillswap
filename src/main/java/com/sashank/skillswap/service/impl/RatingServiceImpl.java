package com.sashank.skillswap.service.impl;

import com.sashank.skillswap.dto.request.CreateRatingRequest;
import com.sashank.skillswap.dto.response.RatingResponse;
import com.sashank.skillswap.dto.response.RatingSummaryResponse;
import com.sashank.skillswap.entity.Rating;
import com.sashank.skillswap.entity.Session;
import com.sashank.skillswap.entity.User;
import com.sashank.skillswap.enums.SessionStatus;
import com.sashank.skillswap.exception.BadRequestException;
import com.sashank.skillswap.exception.ResourceNotFoundException;
import com.sashank.skillswap.repository.RatingRepository;
import com.sashank.skillswap.repository.SessionRepository;
import com.sashank.skillswap.repository.UserRepository;
import com.sashank.skillswap.service.RatingService;
import com.sashank.skillswap.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public RatingResponse rateSession(Long userId, Long sessionId, CreateRatingRequest request) {
        User ratedBy = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        if (!session.getStatus().equals(SessionStatus.COMPLETED)) {
            throw new BadRequestException("Can only rate completed sessions");
        }

        User otherParticipant = session.getExchange().getRequester().getId().equals(userId) ?
                session.getExchange().getReceiver() :
                session.getExchange().getRequester();

        if (ratingRepository.findBySessionIdAndRatedById(sessionId, userId).isPresent()) {
            throw new BadRequestException("You have already rated this session");
        }

        Rating rating = Rating.builder()
                .session(session)
                .ratedBy(ratedBy)
                .ratedTo(otherParticipant)
                .stars(request.getStars())
                .review(request.getReview())
                .build();

        rating = ratingRepository.save(rating);
        return dtoMapper.toRatingResponse(rating);
    }

    @Override
    public List<RatingResponse> getUserRatings(Long userId) {
        return ratingRepository.findByRatedToId(userId).stream()
                .map(dtoMapper::toRatingResponse)
                .toList();
    }

    @Override
    public RatingSummaryResponse getRatingSummary(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Double averageRating = ratingRepository.getAverageRatingByUserId(userId);
        long totalRatings = ratingRepository.countByRatedToId(userId);

        long oneStar = ratingRepository.countByUserAndStars(userId, 1);
        long twoStar = ratingRepository.countByUserAndStars(userId, 2);
        long threeStar = ratingRepository.countByUserAndStars(userId, 3);
        long fourStar = ratingRepository.countByUserAndStars(userId, 4);
        long fiveStar = ratingRepository.countByUserAndStars(userId, 5);

        RatingSummaryResponse.RatingBreakdown breakdown = RatingSummaryResponse.RatingBreakdown.builder()
                .oneStar(oneStar)
                .twoStar(twoStar)
                .threeStar(threeStar)
                .fourStar(fourStar)
                .fiveStar(fiveStar)
                .build();

        return RatingSummaryResponse.builder()
                .averageStars(averageRating != null ? averageRating : 0.0)
                .totalRatings(totalRatings)
                .breakdown(breakdown)
                .build();
    }

    @Override
    public List<RatingResponse> getMyReceivedRatings(Long userId) {
        return ratingRepository.findByRatedToId(userId).stream()
                .map(dtoMapper::toRatingResponse)
                .toList();
    }
}

