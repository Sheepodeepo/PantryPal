package com.PantryPal.service;

import com.PantryPal.model.MealType;
import org.springframework.stereotype.Service;

@Service
public class RecipePromptService {
    /** Create a recipe prompt to use in Gemini(AI Service Class).
     *
     * @param mealType: The type of meal to generate.
     * @param ingredients: Ingredients to be included in recipe prompt.
     * @return (String) => Here's a recipe ... \n#Title: ... #Ingredients: ... Instructions: ...
     * where ingredients and instructions are separated by commas.
     */
    public String createGeminiPrompt(MealType mealType, String ingredients){
        StringBuilder promptBuilder = new StringBuilder("Create a ");
        promptBuilder.append(mealType).append(" recipe");

        if(ingredients != null && !ingredients.isEmpty()){
            promptBuilder.append(" with the ingredients: ").append(ingredients);
        }

        promptBuilder.append(" in the format:<#Title: #Ingredients: #Instructions: . Use commas to separate the list of ingredients and instructions.>");
        return promptBuilder.toString();
    }
}
