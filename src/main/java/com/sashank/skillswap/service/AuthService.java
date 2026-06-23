package com.sashank.skillswap.service;

import com.sashank.skillswap.dto.request.RegisterRequest;
import com.sashank.skillswap.dto.request.LoginRequest;
import com.sashank.skillswap.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}

