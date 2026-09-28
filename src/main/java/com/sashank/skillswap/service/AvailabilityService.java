package com.sashank.skillswap.service;

import com.sashank.skillswap.dto.request.AddAvailabilityRequest;
import com.sashank.skillswap.dto.response.AvailabilityResponse;
import java.util.List;

public interface AvailabilityService {
    AvailabilityResponse addAvailability(Long userId, AddAvailabilityRequest request);
    List<AvailabilityResponse> getAvailability(Long userId);
    void removeAvailability(Long userId, Long availabilityId);
}
