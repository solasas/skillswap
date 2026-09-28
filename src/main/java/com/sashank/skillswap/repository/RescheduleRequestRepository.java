package com.sashank.skillswap.repository;

import com.sashank.skillswap.entity.RescheduleRequest;
import com.sashank.skillswap.enums.RescheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RescheduleRequestRepository extends JpaRepository<RescheduleRequest, Long> {
    List<RescheduleRequest> findBySessionId(Long sessionId);
    boolean existsBySessionIdAndStatus(Long sessionId, RescheduleStatus status);
}
