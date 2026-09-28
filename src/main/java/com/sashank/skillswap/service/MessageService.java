package com.sashank.skillswap.service;

import com.sashank.skillswap.dto.request.SendMessageRequest;
import com.sashank.skillswap.dto.response.ConversationResponse;
import com.sashank.skillswap.dto.response.MessageResponse;
import java.util.List;

public interface MessageService {
    MessageResponse sendMessage(Long senderId, SendMessageRequest request);
    List<MessageResponse> getConversation(Long userId, Long otherUserId);
    List<ConversationResponse> getConversations(Long userId);
    long getUnreadCount(Long userId);
}
