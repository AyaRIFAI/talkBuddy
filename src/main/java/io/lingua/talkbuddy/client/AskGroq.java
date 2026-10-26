package io.lingua.talkbuddy.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lingua.talkbuddy.dto.AnswerofLLM;
import io.lingua.talkbuddy.dto.Body;
import io.lingua.talkbuddy.model.Contexte;
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

    public AnswerofLLM askReply(Contexte contexte) throws JsonProcessingException {
        System.out.println(apiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        Body body = new Body("openai/gpt-oss-120b", contexte.getFinalPrompt());
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

        AnswerofLLM answerofLLM = mapper.readValue(cleanJson(content), AnswerofLLM.class);
        System.out.println(answerofLLM.explanation());
        return answerofLLM;

    }
   /* public void testappel(){
        Client client = new Client();
        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.0-flash",
                        "Explain how AI works in a few words",
                        null);

        System.out.println(response.text());
    }*/

}