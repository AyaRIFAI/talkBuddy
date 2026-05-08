package io.lingua.talkbuddy.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.lingua.talkbuddy.model.Contexte;
import io.lingua.talkbuddy.model.MessageToClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class AskGroq implements AskLLM {
    // Spring générera automatiquement les requêtes sécurisées ici
    @Value("${groq.api-key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public MessageToClient askReply(Contexte contexte) throws JsonProcessingException {
        System.out.println(apiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        MessageRequest messageRequest = new MessageRequest("user", contexte.getFinalPrompt());
        Body body = new Body("llama-3.3-70b-versatile", List.of(messageRequest));
        // 3. Appel
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(body));
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(body), headers);
        System.out.println(request);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.groq.com/openai/v1/chat/completions",
                request,
                String.class
        );

        System.out.println(response.getBody());
        JsonNode root = mapper.readTree(response.getBody());
        String content = root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

        System.out.println(content);

        RecordFromLLM recordFromLLM = mapper.readValue(cleanJson(content), RecordFromLLM.class);
        System.out.println(recordFromLLM.explanation());

        return new MessageToClient(recordFromLLM.reply(), recordFromLLM.translation(), recordFromLLM.correction(),recordFromLLM.explanation(), "Groq");

    }
    public void testappel(){
        Client client = new Client();
        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.0-flash",
                        "Explain how AI works in a few words",
                        null);

        System.out.println(response.text());
    }

}