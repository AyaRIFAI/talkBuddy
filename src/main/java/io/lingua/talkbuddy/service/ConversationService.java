package io.lingua.talkbuddy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.lingua.talkbuddy.client.AskGroq;
import io.lingua.talkbuddy.client.AskMistral;
import io.lingua.talkbuddy.model.Contexte;
import io.lingua.talkbuddy.model.MessageToClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {
    @Autowired
    private AskMistral askMistral;

    @Autowired
    private AskGroq askGroq;
    @Autowired
    private Contexte contexte;

    @CircuitBreaker(name = "buddyApi", fallbackMethod = "callMistral")
    public MessageToClient answerClient(List<String> messages) {
        try {
            contexte.updateContexte(messages);
            return askGroq.askReply(contexte);
        } catch (JsonProcessingException e) {
            return new MessageToClient("Erreur de parsing", null, null, null, "Groq");
        }
    }

    public MessageToClient callMistral(List<String> messages, Throwable t){
        try {
            return askMistral.askReply(contexte);
        } catch (JsonProcessingException e) {
            return new MessageToClient("Erreur de parsing", null, null,null, "Mistral");
        }
    }
}