package com.PantryPal.service;

import com.PantryPal.model.MealType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipePromptServiceTest {

    @Test
    void generateGeminiPromptWithoutIngredientsTest(){
        RecipePromptService genGeminiPrompt = new RecipePromptService();
        String expectedGeminiPromptWithoutIngredients = "Create a BREAKFAST recipe in the format:" +
                "<#Title: #Ingredients: #Instructions: . Use commas to separate the list of ingredients. Use * to separate each line of instruction. Don't include ** in any part of the returned recipe.>";
//        "<#Title: #Ingredients: #Instructions: . Use commas to separate the list of ingredients and instructions.>";
        assertEquals(expectedGeminiPromptWithoutIngredients, genGeminiPrompt.createGeminiPrompt(MealType.BREAKFAST,null));
    }

    @Test
    void generateGeminiPromptWithIngredientsTest(){
        RecipePromptService genGeminiPrompt = new RecipePromptService();
        String expectedGeminiPromptWithoutIngredients = "Create a BREAKFAST recipe with the ingredients: bananas, apples, flour in the format:<#Title: #Ingredients: #Instructions: ."
        + " Use commas to separate the list of ingredients. Use * to separate each line of instruction. Don't include ** in any part of the returned recipe.>";
        assertEquals(expectedGeminiPromptWithoutIngredients, genGeminiPrompt.createGeminiPrompt(MealType.BREAKFAST,"bananas, apples, flour"));
    }

}