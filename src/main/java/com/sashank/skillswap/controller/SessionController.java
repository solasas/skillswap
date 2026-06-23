package com.sashank.skillswap.controller;

import com.sashank.skillswap.dto.request.CreateSessionRequest;
import com.sashank.skillswap.dto.response.SessionResponse;
import com.sashank.skillswap.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/exchanges/{exchangeId}/sessions")
    public ResponseEntity<SessionResponse> createSession(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long exchangeId,
            @Valid @RequestBody CreateSessionRequest request) {
        SessionResponse response = sessionService.createSession(userId, exchangeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/exchanges/{exchangeId}/sessions")
    public ResponseEntity<List<SessionResponse>> getSessionsByExchange(
            @PathVariable Long exchangeId) {
        List<SessionResponse> sessions = sessionService.getSessionsByExchange(exchangeId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/sessions/my-sessions")
    public ResponseEntity<List<SessionResponse>> getUpcomingSessions(
            @RequestAttribute("userId") Long userId) {
        List<SessionResponse> sessions = sessionService.getUpcomingSessions(userId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable Long id) {
        SessionResponse session = sessionService.getSessionById(id);
        return ResponseEntity.ok(session);
    }

    @PutMapping("/sessions/{id}/complete")
    public ResponseEntity<SessionResponse> completeSession(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long id) {
        SessionResponse response = sessionService.completeSession(userId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/sessions/{id}/cancel")
    public ResponseEntity<SessionResponse> cancelSession(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long id) {
        SessionResponse response = sessionService.cancelSession(userId, id);
        return ResponseEntity.ok(response);
    }
}

