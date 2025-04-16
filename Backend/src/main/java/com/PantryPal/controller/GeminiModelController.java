package com.PantryPal.controller;

import com.PantryPal.model.GeminiRequest.GeminiRequestPrompt;
import com.PantryPal.service.AIService;
import org.springframework.web.bind.annotation.*;


@RestController
public class GeminiModelController {
    private AIService aiService;

    public GeminiModelController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/gemini/prompt")
    public String testGeminiAPI(){
        return aiService.sendPostReq("What's the current NBA standings?");
    }

    @PostMapping("/gemini/prompt")
    public String requestGeminiAICall(@RequestBody GeminiRequestPrompt prompt){
        return aiService.sendPostReq(prompt.getPrompt());
    }

}
