package io.lingua.talkbuddy.controller;

import io.lingua.talkbuddy.dto.*;
import io.lingua.talkbuddy.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("/users/{userId}/conversations")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createConversation(@PathVariable Long userId) {
        return conversationService.createConversation(userId);
    }

    @GetMapping("/users/{userId}/conversations")
    public List<ConversationResponse> listConversations(@PathVariable Long userId) {
        return conversationService.listConversations(userId);
    }

    @PostMapping("/conversations/{conversationId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse sendMessage(@PathVariable Long conversationId, @RequestBody MessageRequest message) {
        return conversationService.answerClient(message, conversationId);
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public List<MessageResponse> listMessages(@PathVariable Long conversationId) {
        return conversationService.listMessages(conversationId);
    }
}