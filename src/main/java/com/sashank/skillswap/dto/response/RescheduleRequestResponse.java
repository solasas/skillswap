package com.sashank.skillswap.dto.response;

import com.sashank.skillswap.enums.RescheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RescheduleRequestResponse {
    private Long id;
    private Long sessionId;
    private UserResponse requestedBy;
    private LocalDateTime proposedDateTime;
    private String reason;
    private RescheduleStatus status;
    private LocalDateTime createdAt;
}
