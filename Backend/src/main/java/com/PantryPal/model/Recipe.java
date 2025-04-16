package com.PantryPal.model;

import java.time.Instant;

public class Recipe {
    private long id;
    private String recipeName;
    private MealType mealType;
    private String recipeIngredients;
    private String recipeInstructions;
    private Instant recipeCreatedDate;
    private Instant recipeUpdatedDate;

    public Recipe() {
    }

    public Recipe(String recipeName, MealType mealType, String recipeIngredients, String recipeInstructions, Instant recipeCreatedDate) {
        this.recipeName = recipeName;
        this.mealType = mealType;
        this.recipeIngredients = recipeIngredients;
        this.recipeInstructions = recipeInstructions;
        this.recipeCreatedDate = recipeCreatedDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getRecipeUpdatedDate() {
        return recipeUpdatedDate;
    }

    public void setRecipeUpdatedDate(Instant recipeUpdatedDate) {
        this.recipeUpdatedDate = recipeUpdatedDate;
    }

    public Instant getRecipeCreatedDate() {
        return recipeCreatedDate;
    }

    public void setRecipeCreatedDate(Instant recipeCreatedDate) {
        this.recipeCreatedDate = recipeCreatedDate;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
