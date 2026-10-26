package io.lingua.talkbuddy.controller;

import io.lingua.talkbuddy.dto.*;
import io.lingua.talkbuddy.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conversations")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createConversation(@RequestBody CreateConversationRequest createConversationRequest) {
        return conversationService.createConversation(createConversationRequest);
    }

    /*@GetMapping
    public List<ConversationResponse> listConversations(@RequestParam Long userId) {
        return conversationService.listConversations(userId);
    }*/

    @PostMapping("/{conversationId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse sendMessage(@PathVariable Long conversationId, @RequestBody SendMessageRequest request) {
        return conversationService.answerClient(conversationId, request);
    }

    /*@GetMapping("/{conversationId}/messages")
    public List<MessageResponse> listMessages(@PathVariable Long conversationId) {
        return conversationService.listMessages(conversationId);
    }*/
}