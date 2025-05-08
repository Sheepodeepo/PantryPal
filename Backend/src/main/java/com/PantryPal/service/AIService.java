package com.PantryPal.service;


import com.PantryPal.exceptions.GeminiServiceException;
import com.PantryPal.model.GeminiRequest.*;
import com.PantryPal.model.GeminiResponse.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AIService {
    private static final Logger log = LoggerFactory.getLogger(AIService.class);
    @Value("${spring.ai.gemini.api-key}")
    private String api_key;
    private final String api_endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
    private RestClient restClient;
    private String api_url;
    private GeminiRequestContent requestBody;
    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    public AIService() {
        restClient = RestClient.create();
    }

    public String generateRecipe(String prompt) throws GeminiServiceException {
        // Set Request Body
        requestBody = setUpRequestBody(prompt);
        api_url = api_endpoint + api_key;

        try{
            ResponseEntity<GeminiModelResponse> resEntity = restClient.post()
                    .uri(api_url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .toEntity(GeminiModelResponse.class);

            GeminiModelResponse response = resEntity.getBody();
            HttpStatusCode httpStatusCode = resEntity.getStatusCode();
            if (response != null &&
                    response.getCandidates() != null &&
                    !response.getCandidates().isEmpty() &&
                    response.getCandidates().getFirst() != null &&
                    response.getCandidates().getFirst().getContent() != null &&
                    response.getCandidates().getFirst().getContent().getParts() != null &&
                    !response.getCandidates().getFirst().getContent().getParts().isEmpty() &&
                    response.getCandidates().getFirst().getContent().getParts().getFirst() != null) {
                return response.getCandidates().getFirst().getContent().getParts().getFirst().getText();
            }
            if(httpStatusCode.is5xxServerError()){
                throw new GeminiServiceException("(Gemini) Internal Server Error Message.");
            }
            else{
                throw new GeminiServiceException("Invalid response structure from the API.");
            }
        }
        catch (RestClientException | GeminiServiceException e) {
            logger.error("Error during API call: {}", e.getMessage());
            throw new GeminiServiceException("Error during API call: " + e.getMessage(), e);
        }
    }


    private GeminiRequestContent setUpRequestBody(String prompt){
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
