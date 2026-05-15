package io.lingua.talkbuddy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.lingua.talkbuddy.client.AskGroq;
import io.lingua.talkbuddy.client.AskMistral;
import io.lingua.talkbuddy.model.Contexte;
import io.lingua.talkbuddy.model.MessageFromClient;
import io.lingua.talkbuddy.model.MessageToClient;
import io.lingua.talkbuddy.model.Replique;
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
    public MessageToClient answerClient(MessageFromClient message) {
        try {

            contexte.updateContexte(new Replique("Utilisateur", message.getMessage()));
            MessageToClient msgToClient = askGroq.askReply(contexte);
            contexte.updateContexte(new Replique("Professeur", msgToClient.getAnswerofLLM()));
            return msgToClient;
        } catch (JsonProcessingException e) {
            return new MessageToClient("Erreur de parsing", null, null, null, "Groq");
        }
    }

    public MessageToClient callMistral(MessageFromClient message, Throwable t){
        try {
            contexte.updateContexte(new Replique("Utilisateur", message.getMessage()));
            MessageToClient msgToClient =  askMistral.askReply(contexte);
            contexte.updateContexte(new Replique("Professeur", msgToClient.getAnswerofLLM()));
            return msgToClient;
        } catch (JsonProcessingException e) {
            return new MessageToClient("Erreur de parsing", null, null,null, "Mistral");
        }
    }
}