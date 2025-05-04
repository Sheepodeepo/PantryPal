package com.PantryPal.dto;

import com.PantryPal.model.MealType;

public class CreateRecipeDto {
    private String mealType;
    private String recipeIngredients;

    public CreateRecipeDto() {
    }

    public CreateRecipeDto(String mealType, String recipeIngredients) {
        this.mealType = mealType;
        this.recipeIngredients = recipeIngredients;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

}
