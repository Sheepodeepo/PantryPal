package com.PantryPal.service;

import com.PantryPal.model.MealType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateGeminiPromptTest {

    @Test
    void generateGeminiPromptWithoutIngredientsTest(){
        GenerateGeminiPrompt genGeminiPrompt = new GenerateGeminiPrompt();
        String expectedGeminiPromptWithoutIngredients = """
                Generate a Breakfast recipe in the format: \
                Title:\s
                Ingredients:\s
                Instructions: \
                """;
        assertEquals(expectedGeminiPromptWithoutIngredients, genGeminiPrompt.createGeminiPrompt(MealType.BREAKFAST,null));
    }

    @Test
    void generateGeminiPromptWithIngredientsTest(){
        GenerateGeminiPrompt genGeminiPrompt = new GenerateGeminiPrompt();
        String expectedGeminiPromptWithoutIngredients = """
                Generate a Breakfast recipe with the ingredients: bananas, apples, flour in the format: \
                Title:\s
                Ingredients:\s
                Instructions: \
                """;
        assertEquals(expectedGeminiPromptWithoutIngredients, genGeminiPrompt.createGeminiPrompt(MealType.BREAKFAST,"bananas, apples, flour"));
    }

}