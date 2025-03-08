package com.artglimpse.controller.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.artglimpse.model.chatbot.ChatRequest;
import com.artglimpse.model.chatbot.ChatResponse;
import com.artglimpse.model.chatbot.OpenAiService;

@RestController
@RequestMapping("/api")
@CrossOrigin // Enable CORS for your React app
public class ChatbotController {

    @Autowired
    private OpenAiService openAiService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest chatRequest) {
        String userMessage = chatRequest.getMessage();
        String botResponse = openAiService.getChatResponse(userMessage);
        return new ChatResponse(botResponse);
    }
}
