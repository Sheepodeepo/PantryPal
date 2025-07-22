package com.PantryPal.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.PantryPal.auth.MyUserDetails;
import com.PantryPal.dto.CreateRecipeReqBodyDto;
import com.PantryPal.dto.RecipeDto;
import com.PantryPal.exceptions.GeminiServiceException;
import com.PantryPal.model.MealType;
import com.PantryPal.model.Recipe;
import com.PantryPal.repository.RecipeRepository;
import com.PantryPal.service.AIService;
import com.PantryPal.service.FavoriteService;
import com.PantryPal.service.RecipePromptService;

/** Maybe use Spring Security ALC >> Authenication.get()... for scalability.
 *  Todos: Update Recipe Controllers to authenticate if recipes correlate to authentication.
 */
@RestController
//@RequestMapping("api/v1/recipe")
public class RecipeController {
    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final AIService aiService;
    private final RecipeRepository repository;
    private final RecipePromptService recipePromptService;
    private final FavoriteService favoriteService;

    public RecipeController(RecipeRepository repository, AIService aiService, RecipePromptService recipePromptService, FavoriteService favoriteService) {
        this.repository = repository;
        this.aiService = aiService;
        this.recipePromptService = recipePromptService;
        this.favoriteService = favoriteService;
    }

    @PostMapping("/api/v1/recipe/favorite")
    public ResponseEntity<String> addFavoriteRecipe(@RequestBody RecipeDto recipeDto){
        MyUserDetails curUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = curUser.getId();
        favoriteService.addFavorite(userId, recipeDto.getRecipeId());
        return ResponseEntity.ok("Recipe added to favorites");
    }

    @GetMapping("/api/v1/recipe/favorite/top3")
    public ResponseEntity<List<Recipe>> getTopThreeMostFavoriteRecipes(){
        return new ResponseEntity<>(favoriteService.getTopFavoriteRecipes(3), HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/v1/recipe/favorite")
    public ResponseEntity<List<Recipe>> getUserFavoriteRecipes(){
        MyUserDetails curUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = curUser.getId();
        List<Recipe> recipeLst = favoriteService.getUserFavorites(userId);
        return new ResponseEntity<>(recipeLst, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("api/v1/recipe/favorite")
    public void removeFavoriteRecipe(@RequestBody RecipeDto recipeDto){
        MyUserDetails curUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = curUser.getId();
        favoriteService.removeFavorite(userId, recipeDto.getRecipeId());
    }

    @GetMapping("/api/v1/recipe")
    public ResponseEntity<List<Recipe>> getAllRecipesFromUser(){
        MyUserDetails curUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = curUser.getId();
        return new ResponseEntity<>(repository.findAllByUserId(userId), HttpStatus.ACCEPTED);
    }

    @GetMapping("api/v1/recipe/{recipe_id}")
    public ResponseEntity<Recipe> findRecipeByRecipeId(@PathVariable Long recipe_id){
//        Add a check if it is their own recipe_id
        return buildRecipeResponse(checkValidRecipe(repository.findById(recipe_id)));
    }

    @PostMapping("api/v1/recipe")
    public ResponseEntity<Recipe> generateRecipe(@RequestBody CreateRecipeReqBodyDto recipeDto) throws GeminiServiceException {
        MyUserDetails curUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = curUser.getId();

        String recipeMealTypeStr = recipeDto.getMealType().toUpperCase();
        MealType recipeMealType = MealType.valueOf(recipeMealTypeStr);
        String userIngredients = recipeDto.getRecipeIngredients();
        
        String recipePrompt = createRecipePrompt(recipeMealType, userIngredients);
        String recipeStr = generateRecipeStrWithAI(recipePrompt);

        String[] parsedStr = parseRecipeStrToArr(recipeStr);

        String recipeName = stripRecipeDetailsFromParsedString(parsedStr,0);
        String recipeIngredients = stripRecipeDetailsFromParsedString(parsedStr,1);
        String recipeInstructions = stripRecipeDetailsFromParsedString(parsedStr, 2);

        Recipe newRecipe = new Recipe(userId, recipeName, recipeMealType, recipeIngredients, recipeInstructions);
        repository.save(newRecipe);
        return buildRecipeResponse(newRecipe);
    }

    @PutMapping("api/v1/recipe")
    public ResponseEntity<Recipe> updateRecipeById(@PathVariable Long id,
                                               @RequestBody Recipe updatedRecipe)
    {
        MyUserDetails curUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = curUser.getId();

        // Restrict Update if given recipe's Ids user_id isn't curUserId

        Optional<Recipe> optionalRecipe = repository.findById(id);
        if(optionalRecipe.isPresent()){
            // Update Recipe
            Recipe curRecipe = optionalRecipe.get();
            curRecipe.setName(updatedRecipe.getName());
            curRecipe.setMealType(updatedRecipe.getMealType());
            curRecipe.setIngredients(updatedRecipe.getIngredients());
            curRecipe.setInstructions(updatedRecipe.getInstructions());
            curRecipe.setUpdatedDate(LocalDate.now());
            return buildRecipeResponse(curRecipe);
        }
        updatedRecipe.setUpdatedDate(LocalDate.now());
        return buildRecipeResponse(updatedRecipe);
    }

    @DeleteMapping("api/v1/recipe/{id}")
    public ResponseEntity<String> deleteRecipeById(@PathVariable Long id){
        MyUserDetails curUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = curUser.getId();

        Recipe recipe = checkValidRecipe(repository.findById(id));
        if(recipe == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.delete(recipe);
        return new ResponseEntity<>("Deleted Recipe: " + recipe.getName() + " successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping("/test/test")
    public String testEndpoint(){
        return "Updated Testing Page";
    }

    private String createRecipePrompt(MealType mealType, String ingredients){
        return recipePromptService.createGeminiPrompt(mealType, ingredients);
    }

    private String generateRecipeStrWithAI(String recipePrompt) throws GeminiServiceException {
        return aiService.generateRecipe(recipePrompt);
    }

    private Recipe checkValidRecipe(Optional<Recipe> optionalRecipe){
        return optionalRecipe.orElse(null);
    }

    private ResponseEntity<Recipe> buildRecipeResponse(Recipe recipe){
        if(recipe == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipe, HttpStatus.ACCEPTED);
    }

    /**Given recipeStr from AIService(Gemini): Parse into 3 sections: Name, Ingredients, Instructions
     *
     * @param recipeStr Generated Recipe in a String with Name, Ingredients, Instructions
     * @return String[] where str[0]: prompt text, str[1]: Title: ..., str[2] Ingredients: \n ..., str[3]: Instructions: ...
     */
    private String[] parseRecipeStrToArr(String recipeStr){
        String[] parsedRecipe = recipeStr.split("#");

        //Get Name:
        String[] recipeDetails = new String[3];
        recipeDetails[0] = parsedRecipe[1];
        recipeDetails[1] = parsedRecipe[2];
        recipeDetails[2] = parsedRecipe[3];

        //Return String of <Name>,<Ingredients>, <Instructions>
        return recipeDetails;
    }

    /**Given the parsed Sections of a Recipe and the index, return the stripped details
     *
     * @param formatStr: String array of Length 3 where each value is => Name: [Recipe Name]
     * @param index: The index of the section we want. 0 => Name, 1 => Ingredients, 2 => Instructions.
     * @return String
     */
    private String stripRecipeDetailsFromParsedString(String[] formatStr, int index ){
        return formatStr[index].split(":")[1].strip();
    }
}
