package com.sashank.skillswap.service.impl;

import com.sashank.skillswap.dto.request.AddAvailabilityRequest;
import com.sashank.skillswap.dto.response.AvailabilityResponse;
import com.sashank.skillswap.entity.Availability;
import com.sashank.skillswap.entity.User;
import com.sashank.skillswap.exception.BadRequestException;
import com.sashank.skillswap.exception.ResourceNotFoundException;
import com.sashank.skillswap.repository.AvailabilityRepository;
import com.sashank.skillswap.repository.UserRepository;
import com.sashank.skillswap.service.AvailabilityService;
import com.sashank.skillswap.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public AvailabilityResponse addAvailability(Long userId, AddAvailabilityRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        Availability availability = Availability.builder()
                .user(user)
                .dayOfWeek(request.getDayOfWeek())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        availability = availabilityRepository.save(availability);
        return dtoMapper.toAvailabilityResponse(availability);
    }

    @Override
    public List<AvailabilityResponse> getAvailability(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return availabilityRepository.findByUserIdOrderByDayOfWeekAscStartTimeAsc(userId).stream()
                .map(dtoMapper::toAvailabilityResponse)
                .toList();
    }

    @Override
    public void removeAvailability(Long userId, Long availabilityId) {
        Availability availability = availabilityRepository.findByIdAndUserId(availabilityId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Availability slot not found"));

        availabilityRepository.delete(availability);
    }
}
