package com.PantryPal.controller;

import com.PantryPal.model.MealType;
import com.PantryPal.model.Recipe;
import com.PantryPal.repository.RecipeRepository;
import com.PantryPal.service.AIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RecipeController {
    private AIService aiService;
    private RecipeRepository repository;

    public RecipeController(RecipeRepository repository, AIService aiService) {
        this.repository = repository;
        this.aiService = aiService;
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

//    @PostMapping("/recipe")
//    public Recipe generateRecipe(@RequestParam String prompt,
//                               @RequestParam MealType mealType) {
//
//    }

    @PutMapping("/recipe")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id){
        return null;
    }
    @DeleteMapping("/recipe")
    public ResponseEntity<String> deleteRecipeByName(@RequestParam String recipeName){
        repository.deleteByRecipeName(recipeName);
        return new ResponseEntity<>("Deleted Recipe: " + recipeName + " successfully", HttpStatus.ACCEPTED);
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
}
