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
    public class AskMistral implements AskLLM {

        @Value("${mistral.api-key}")
        private String apiKey;

        private final RestTemplate restTemplate = new RestTemplate();


        public AnswerofLLM askReply(Contexte contexte) throws JsonProcessingException {

            // 1. Headers
            HttpHeaders headers = new HttpHeaders();
            System.out.println(apiKey);
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("Content-Type", "application/json");

            Body body = new Body("mistral-small-latest", contexte.getFinalPrompt());
            // 3. Appel
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(body));
            HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(body), headers);
            System.out.println(request);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.mistral.ai/v1/chat/completions",
                    request,
                    String.class
            );

            //System.out.println(response.getBody());
            mapper = new ObjectMapper();
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
    }
