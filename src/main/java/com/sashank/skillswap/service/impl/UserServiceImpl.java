package com.sashank.skillswap.service.impl;

import com.sashank.skillswap.dto.request.UpdateProfileRequest;
import com.sashank.skillswap.dto.response.ProfileResponse;
import com.sashank.skillswap.dto.response.UserSkillResponse;
import com.sashank.skillswap.entity.User;
import com.sashank.skillswap.entity.UserSkill;
import com.sashank.skillswap.enums.SkillType;
import com.sashank.skillswap.exception.ResourceNotFoundException;
import com.sashank.skillswap.repository.RatingRepository;
import com.sashank.skillswap.repository.UserRepository;
import com.sashank.skillswap.repository.UserSkillRepository;
import com.sashank.skillswap.service.UserService;
import com.sashank.skillswap.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public ProfileResponse getProfile(Long userId) {
        User user = getUserById(userId);

        List<UserSkill> teachSkills = userSkillRepository.findByUserIdAndType(userId, SkillType.TEACH);
        List<UserSkill> learnSkills = userSkillRepository.findByUserIdAndType(userId, SkillType.LEARN);

        Double avgRating = ratingRepository.getAverageRatingByUserId(userId);
        long totalRatings = ratingRepository.countByRatedToId(userId);

        return ProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .city(user.getCity())
                .teachSkills(dtoMapper.toUserSkillResponseList(teachSkills))
                .learnSkills(dtoMapper.toUserSkillResponseList(learnSkills))
                .averageRating(avgRating != null ? avgRating : 0.0)
                .totalRatings(totalRatings)
                .build();
    }

    @Override
    public ProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = getUserById(userId);

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }
        if (request.getCity() != null) {
            user.setCity(request.getCity());
        }

        user = userRepository.save(user);

        return getProfile(user.getId());
    }
}

