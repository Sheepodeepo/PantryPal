package com.PantryPal.dto;

public class RecipeDto {
    private long recipeId;

    public RecipeDto(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }
}
