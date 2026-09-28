package com.sashank.skillswap.controller;

import com.sashank.skillswap.dto.request.SendMessageRequest;
import com.sashank.skillswap.dto.response.MessageResponse;
import com.sashank.skillswap.entity.User;
import com.sashank.skillswap.exception.ResourceNotFoundException;
import com.sashank.skillswap.repository.UserRepository;
import com.sashank.skillswap.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class ChatWebSocketController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Valid SendMessageRequest request, Principal principal) {
        User sender = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MessageResponse response = messageService.sendMessage(sender.getId(), request);

        messagingTemplate.convertAndSendToUser(response.getReceiver().getEmail(), "/queue/messages", response);
        messagingTemplate.convertAndSendToUser(response.getSender().getEmail(), "/queue/messages", response);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Exception exception) {
        return exception.getMessage();
    }
}
