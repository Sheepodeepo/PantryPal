package com.PantryPal.service;


import com.PantryPal.controller.GeminiModelController;
import com.PantryPal.model.GeminiRequest.*;
import com.PantryPal.model.GeminiResponse.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;


@Service
public class AIService {
    private static final Logger log = LoggerFactory.getLogger(AIService.class);
    @Value("${spring.ai.gemini.api-key}")
    private String api_key;
    private final String api_endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
    private RestClient restClient;
    private String api_url;
    private GeminiRequestContent requestBody;
    private GeminiModelResponse responseBody;

    public AIService() {
        restClient = RestClient.create();
    }

    public String testAPIReq(){
        requestBody = setUpRequestBody("Share a fun fact about LLM's");
        api_url = api_endpoint + api_key;

        ResponseEntity<String> resEntity = restClient.post()
                .uri(api_url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        return resEntity.getBody();
    }

    public String sendPostReq(String prompt){
        // Set Request Body
        requestBody = setUpRequestBody(prompt);
        api_url = api_endpoint + api_key;

        ResponseEntity<GeminiModelResponse> resEntity = restClient.post()
                .uri(api_url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(GeminiModelResponse.class);

        GeminiModelResponse response = resEntity.getBody();
        return response.getCandidates().getFirst().getContent().getParts().getFirst().getText();
    }


    public GeminiRequestContent setUpRequestBody(String prompt){
        GeminiRequestText text = new GeminiRequestText(prompt);
        ArrayList<GeminiRequestText> textList = new ArrayList<>();
        textList.add(text);
        GeminiRequestParts geminiParts = new GeminiRequestParts(textList);
        List<GeminiRequestParts> partsList = new ArrayList<>();
        partsList.add(geminiParts);
        requestBody = new GeminiRequestContent(partsList);
        return requestBody;
    }
}
