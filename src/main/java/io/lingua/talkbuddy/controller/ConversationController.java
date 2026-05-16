package io.lingua.talkbuddy.controller;

import io.lingua.talkbuddy.entity.ConversationEntity;
import io.lingua.talkbuddy.model.MessageFromClient;
import io.lingua.talkbuddy.model.MessageToClient;
import io.lingua.talkbuddy.service.ConversationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @PostMapping("/conversation/new")
    public MessageToClient createConversation(@RequestBody Long userId) {
        return conversationService.createConversation(userId);

    }
    @PostMapping("/conversation/message")
    public MessageToClient chatWithBuddy(@RequestBody MessageFromClient message) {
        return conversationService.answerClient(message);

    }
}