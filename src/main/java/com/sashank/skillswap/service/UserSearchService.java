package com.sashank.skillswap.service;

import com.sashank.skillswap.dto.response.UserResponse;
import com.sashank.skillswap.enums.SkillLevel;
import java.util.List;

public interface UserSearchService {
    List<UserResponse> searchUsers(Long currentUserId, String skillName, String city,
                                    SkillLevel level, Double minRating, Boolean availableOnly);
}
