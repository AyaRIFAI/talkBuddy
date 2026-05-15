package io.lingua.talkbuddy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.lingua.talkbuddy.model.MessageFromClient;
import io.lingua.talkbuddy.model.MessageToClient;
import io.lingua.talkbuddy.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @PostMapping("/conversation")
    public MessageToClient chatWithBuddy(@RequestBody MessageFromClient message) {
        return conversationService.answerClient(message);

    }
}