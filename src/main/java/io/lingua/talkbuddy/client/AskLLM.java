package io.lingua.talkbuddy.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.lingua.talkbuddy.dto.AnswerofLLM;
import io.lingua.talkbuddy.model.Contexte;
import java.util.List;

public interface AskLLM {
    // Spring générera automatiquement les requêtes sécurisées ici
    //public record MessageRequest(String role, String content) {};
    //public record Body(String model, List<MessageRequest> messages) {};
   public AnswerofLLM askReply(Contexte contexte) throws JsonProcessingException;

   public default String cleanJson(String rawContent) {
        if (rawContent == null) return null;

        String clean = rawContent.trim();

        // Si ça commence par ```json et finit par ```
        if (clean.startsWith("```json")) {
            clean = clean.substring(7);
        } else if (clean.startsWith("```")) {
            clean = clean.substring(3);
        }

        if (clean.endsWith("```")) {
            clean = clean.substring(0, clean.length() - 3);
        }

        return clean.trim();
    }
}