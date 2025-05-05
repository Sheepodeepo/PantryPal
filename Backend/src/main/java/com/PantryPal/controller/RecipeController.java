package com.PantryPal.controller;

import com.PantryPal.dto.CreateRecipeReqBodyDto;
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
        return buildRecipeResponse(checkValidRecipe(repository.findById(id)));
    }


    @PostMapping("/recipe")
    public ResponseEntity<Recipe> generateRecipe(@RequestBody CreateRecipeReqBodyDto recipeDto) {
        String recipeMealTypeStr = recipeDto.getMealType().toUpperCase();
        MealType recipeMealType = MealType.valueOf(recipeMealTypeStr);
        String userIngredients = recipeDto.getRecipeIngredients();
        
        String recipePrompt = createRecipePrompt(recipeMealType, userIngredients);
        String recipeStr = generateRecipeString(recipePrompt);


        // Parse recipe String
        String[] parsedStr = parseRecipeStringToRecipe(recipeStr); //Name:, Ingredients:, Instructions:

        String recipeName = parsedStr[0].split(":")[1].strip();
        String recipeIngredients = parsedStr[1].split(":")[1].strip();
        String recipeInstructions = parsedStr[2].split(":")[1].strip();

        Recipe newRecipe = new Recipe(recipeName, recipeMealType, recipeIngredients, recipeInstructions);
        repository.save(newRecipe);
        return buildRecipeResponse(newRecipe);
    }

    @PutMapping("/recipe")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id,
                                               @RequestBody Recipe updatedRecipe){
        if(repository.findById(id).isPresent()){
            // Add
        }
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

    private Recipe checkValidRecipe(Optional<Recipe> optionalRecipe){
//        if(optionalRecipe.isEmpty()){
//            return null;
//        }
//        return optionalRecipe.get();
        return optionalRecipe.orElse(null);
    }

    private ResponseEntity<Recipe> buildRecipeResponse(Recipe recipe){
        if(recipe == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipe, HttpStatus.ACCEPTED);
    }

    /**
     *
     * @param recipeStr
     * @return String[] where str[0]: prompt text, str[1]: Title: ..., str[2] Ingredients: \n ..., str[3]: Instructions: ...
     */
    private String[] parseRecipeStringToRecipe(String recipeStr){
        //Parse recipeString
        //parsedRecipe: Title: ___ #Ingredients: ___ #Instructions: ___
        String[] parsedRecipe = recipeStr.split("#");

        //Get Name:
        String[] recipeDetails = new String[3];
        recipeDetails[0] = parsedRecipe[1];
        recipeDetails[1] = parsedRecipe[2];
        recipeDetails[2] = parsedRecipe[3];

        //Return String of <Name>,<Ingredients>, <Instructions>
        return recipeDetails;
    }
}
