package com.PantryPal.controller;

import com.PantryPal.dto.CreateRecipeDto;
import com.PantryPal.model.MealType;
import com.PantryPal.model.Recipe;
import com.PantryPal.repository.RecipeRepository;
import com.PantryPal.service.AIService;
import com.PantryPal.service.RecipePromptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RecipeController {
    private AIService aiService;
    private RecipeRepository repository;
    private RecipePromptService recipePromptService;

    public RecipeController(RecipeRepository repository, AIService aiService, RecipePromptService recipePromptService) {
        this.repository = repository;
        this.aiService = aiService;
        this.recipePromptService = recipePromptService;
    }

    @GetMapping("/recipe/all")
    public ResponseEntity<List<Recipe>> getAllRecipes(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> findRecipeById(@PathVariable Long id){
        return buildRecipeResponse(repository.findById(id));
    }

    @GetMapping("/recipe")
    public ResponseEntity<Recipe> findRecipeByName(@RequestParam String recipeName){
        return buildRecipeResponse(repository.findByRecipeName(recipeName));
    }

    @PostMapping("/recipe")
    public String generateRecipe(@RequestBody CreateRecipeDto recipeDto) {
        String recipeMealTypeStr = recipeDto.getMealType().toUpperCase();
        MealType recipeMealType = MealType.valueOf(recipeMealTypeStr);
        String ingredients = recipeDto.getRecipeIngredients();
        
        String recipePrompt = createRecipePrompt(recipeMealType, ingredients);
        String recipeStr = generateRecipeString(recipePrompt);

        // Parse recipe String
        return recipeStr;
    }

    @PutMapping("/recipe")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id){
        return null;
    }

    @DeleteMapping("/recipe")
    public ResponseEntity<String> deleteRecipeByName(@RequestParam String recipeName){
        repository.deleteByRecipeName(recipeName);
        return new ResponseEntity<>("Deleted Recipe: " + recipeName + " successfully", HttpStatus.ACCEPTED);
    }

    /**
     *
     * @param mealType
     * @param ingredients, if no ingredients prompted => empty string
     * @return
     */
    private String createRecipePrompt(MealType mealType, String ingredients){
        return recipePromptService.createGeminiPrompt(mealType, ingredients);
    }

    private String generateRecipeString(String recipePrompt){
        return aiService.generateRecipe(recipePrompt);
    }

    private ResponseEntity<Recipe> buildRecipeResponse(Optional<Recipe> optionalRecipe){
        if(optionalRecipe.isPresent()){
            return new ResponseEntity<>(optionalRecipe.get(), HttpStatus.ACCEPTED);
        }
        else if(optionalRecipe.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String[] parseRecipeStringToRecipe(String recipeStr){
        String[] recipeDetails = new String[3]; // String array containing: <Name>, <Ingredients>, <Instructions>

        //Parse recipeString
        //parsedRecipe: Title: ___ #Ingredients: ___ #Instructions: ___
        String[] parsedRecipe = recipeStr.split(":");


        //Get Name:


        return recipeDetails;
    }
}
