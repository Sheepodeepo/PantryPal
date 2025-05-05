package com.PantryPal.service;

import com.PantryPal.model.MealType;
import org.springframework.stereotype.Service;

@Service
public class RecipePromptService {
    /**
     * Create a recipe prompt to use in Gemini.
     *
     * @param mealType: The type of meal to generate.
     * @param ingredients: Ingredients to be included in recipe prompt.
     * @return String in the format: Title: ___ Ingredients: ___ Instructions: ___
     */
    public String createGeminiPrompt(MealType mealType, String ingredients){
        StringBuilder promptBuilder = new StringBuilder("Create a ");
        promptBuilder.append(mealType.getDisplayName()).append(" recipe");

        if(ingredients != null && !ingredients.isEmpty()){
            promptBuilder.append(" with the ingredients: ").append(ingredients);
        }

        promptBuilder.append(" in the format:<#Title: #Ingredients: #Instructions: . Use commas to separate the list of ingredients and instructions.>");
        return promptBuilder.toString();
    }
}
