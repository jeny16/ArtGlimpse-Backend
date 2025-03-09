package com.artglimpse.buyer.model.chatbot;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {
    
    private final String apiKey;
    private final RestTemplate restTemplate;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public OpenAiService(@Value("${ChatbotApi}") String apiKey) {
        this.apiKey = apiKey;
        this.restTemplate = new RestTemplate();
    }

    @SuppressWarnings("unchecked")
    public String getChatResponse(String userMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "user", "content", userMessage));
            requestBody.put("messages", messages);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);

            // Extract the chatbot response safely
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (!choices.isEmpty() && choices.get(0).containsKey("message")) {
                    Map<String, Object> messageObj = (Map<String, Object>) choices.get(0).get("message");
                    return messageObj.get("content").toString().trim();
                }
            }
            return "No response from API.";
        } catch (Exception e) {
            return "Error communicating with OpenAI API: " + e.getMessage();
        }
    }
}
