package com.sashank.skillswap.controller;

import com.sashank.skillswap.dto.response.AvailabilityResponse;
import com.sashank.skillswap.dto.response.UserResponse;
import com.sashank.skillswap.enums.SkillLevel;
import com.sashank.skillswap.service.AvailabilityService;
import com.sashank.skillswap.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(
            @RequestAttribute("userId") Long currentUserId,
            @RequestParam(required = false) String skillName,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) SkillLevel level,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Boolean availableOnly) {
        List<UserResponse> users = userSearchService.searchUsers(
                currentUserId, skillName, city, level, minRating, availableOnly);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}/availability")
    public ResponseEntity<List<AvailabilityResponse>> getUserAvailability(@PathVariable Long userId) {
        List<AvailabilityResponse> availability = availabilityService.getAvailability(userId);
        return ResponseEntity.ok(availability);
    }
}
