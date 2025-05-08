package com.PantryPal.service;

import com.PantryPal.exceptions.GeminiServiceException;
import com.PantryPal.model.GeminiRequest.GeminiRequestContent;
import com.PantryPal.model.GeminiRequest.GeminiRequestParts;
import com.PantryPal.model.GeminiRequest.GeminiRequestText;
import com.PantryPal.model.GeminiResponse.GeminiCandidate;
import com.PantryPal.model.GeminiResponse.GeminiContent;
import com.PantryPal.model.GeminiResponse.GeminiModelResponse;
import com.PantryPal.model.GeminiResponse.GeminiPart;
import com.PantryPal.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AIServiceTest {

    //Use Mockito to mock Gemini API.
    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RestClient.RequestBodySpec requestBodySpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private AIService aiService;

    private ResponseEntity<GeminiModelResponse> responseEntity;

    private final String EXPECTED_URI = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=null";
    private final String TEST_PROMPT = "Generate a recipe with chicken and tomatoes";
    private final String EXPECTED_RECIPE = "Delicious Chicken and Tomato Recipe...";

    @Test
    public void setUpRequestBodyTest() {
        // Using reflection to test the private method
        GeminiRequestContent result = ReflectionTestUtils.invokeMethod( //Optional type Cast to GeminiRequestContent from Generic Object
                aiService, "setUpRequestBody", TEST_PROMPT);

        // Verify the request structure
        assertNotNull(result);
        assertNotNull(result.getContents());
        assertEquals(1, result.getContents().size());

        GeminiRequestParts parts = result.getContents().getFirst();
        assertNotNull(parts);
        assertNotNull(parts.getParts());
        assertEquals(1, parts.getParts().size());

        GeminiRequestText textObj = parts.getParts().getFirst();
        assertNotNull(textObj);
        assertEquals(TEST_PROMPT, textObj.getText());
    }

    @Test
    void generateRecipeTestWithPromptSuccess() throws GeminiServiceException {
        GeminiRequestContent expectedResponse = ReflectionTestUtils.invokeMethod( //Optional type Cast to GeminiRequestContent from Generic Object
                aiService, "setUpRequestBody", TEST_PROMPT);

        assertNotNull(expectedResponse);

        //Stub the restClient code, basically mocking how restClient calls it
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(EXPECTED_URI)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(GeminiRequestContent.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        // Mock the RestClient response
        GeminiModelResponse geminiResponse = setUpModelResponse(EXPECTED_RECIPE);
        responseEntity = ResponseEntity.ok(geminiResponse);

        //Stub the GeminiResponse Entity
        when(responseSpec.toEntity(GeminiModelResponse.class)).thenReturn(responseEntity);

        // Call the method
        String recipe = aiService.generateRecipe(TEST_PROMPT);

        // Verify the result
        assertEquals(EXPECTED_RECIPE, recipe);

        // Verify that the RestClient was called as expected
        verify(restClient, times(1)).post(); //times(1) is default so not needed
        verify(requestBodyUriSpec).uri(eq(EXPECTED_URI));
        verify(requestBodySpec).contentType(MediaType.APPLICATION_JSON);
        verify(requestBodySpec).body(any(GeminiRequestContent.class));
        verify(requestBodySpec).retrieve();
        verify(responseSpec).toEntity(GeminiModelResponse.class);

        // Capture the actual argument passed to body()
        ArgumentCaptor<GeminiRequestContent> requestContentCaptor = ArgumentCaptor.forClass(GeminiRequestContent.class);
        verify(requestBodySpec).body(requestContentCaptor.capture());

        // Now assert that the captured argument is equal to your expected object
        assertNotNull(expectedResponse.getContents().getFirst().getParts().getFirst().getText());
        assertNotNull(requestContentCaptor.getValue().getContents().getFirst().getParts().getFirst().getText());

        assertEquals(expectedResponse.getContents().getFirst().getParts().getFirst().getText(), requestContentCaptor.getValue().getContents().getFirst().getParts().getFirst().getText());
    }

    private GeminiModelResponse setUpModelResponse(String modelResponse){
        GeminiModelResponse response = new GeminiModelResponse();
        GeminiCandidate candidate = new GeminiCandidate();
        GeminiContent content = new GeminiContent();
        GeminiPart part = new GeminiPart();

        ///
        part.setText(modelResponse);
        content.setParts(List.of(part));
        candidate.setContent(content);
        response.setCandidates(List.of(candidate));
        return response;
    }

}

/**Argument Captor: https://javadoc.io/static/org.mockito/mockito-core/5.17.0/org.mockito/org/mockito/Mockito.html#15
 *
 *
 *
 *
 *
 */