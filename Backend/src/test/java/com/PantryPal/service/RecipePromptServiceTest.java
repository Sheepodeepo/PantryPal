package com.PantryPal.service;

import com.PantryPal.model.MealType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipePromptServiceTest {

    @Test
    void generateGeminiPromptWithoutIngredientsTest(){
        RecipePromptService genGeminiPrompt = new RecipePromptService();
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
        RecipePromptService genGeminiPrompt = new RecipePromptService();
        String expectedGeminiPromptWithoutIngredients = """
                Generate a Breakfast recipe with the ingredients: bananas, apples, flour in the format: \
                Title:\s
                Ingredients:\s
                Instructions: \
                """;
        assertEquals(expectedGeminiPromptWithoutIngredients, genGeminiPrompt.createGeminiPrompt(MealType.BREAKFAST,"bananas, apples, flour"));
    }

}