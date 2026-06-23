package com.sashank.skillswap.service;

import com.sashank.skillswap.dto.request.UpdateProfileRequest;
import com.sashank.skillswap.dto.response.ProfileResponse;
import com.sashank.skillswap.entity.User;

public interface UserService {
    User getUserById(Long userId);
    ProfileResponse getProfile(Long userId);
    ProfileResponse updateProfile(Long userId, UpdateProfileRequest request);
}

