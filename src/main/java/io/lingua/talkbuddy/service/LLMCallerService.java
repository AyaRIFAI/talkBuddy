package io.lingua.talkbuddy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.lingua.talkbuddy.client.AskGroq;
import io.lingua.talkbuddy.client.AskMistral;
import io.lingua.talkbuddy.dto.AnswerofLLM;
import io.lingua.talkbuddy.model.Contexte;
import org.springframework.stereotype.Service;

@Service
public class LLMCallerService {
    private AskGroq askGroq;
    private AskMistral askMistral;

    public LLMCallerService(AskGroq askGroq, AskMistral askMistral) {
        this.askGroq = askGroq;
        this.askMistral = askMistral;
    }

    @CircuitBreaker(name = "buddyApi", fallbackMethod = "callMistral")
    public AnswerofLLM callGroq(Contexte contexte) {
        try {
            return askGroq.askReply(contexte);

        } catch (JsonProcessingException e) {
            return new AnswerofLLM("Erreur de parsing", null, null, null);
        }
    }
    public AnswerofLLM callMistral(Contexte contexte,Throwable t){
        try {
            //return askMistral.askReply(contexte);
            return askGroq.askReply(contexte);

        } catch (JsonProcessingException e) {
            return new AnswerofLLM("Erreur de parsing", null, null,null);
        }
    }
}
