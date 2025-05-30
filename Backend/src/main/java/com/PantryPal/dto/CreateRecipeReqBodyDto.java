package com.PantryPal.dto;

public class CreateRecipeReqBodyDto {
    private long user_id;
    private String mealType;
    private String recipeIngredients;

    public CreateRecipeReqBodyDto() {
    }

    public CreateRecipeReqBodyDto(String mealType, String recipeIngredients) {
        this.mealType = mealType;
        this.recipeIngredients = recipeIngredients;
    }

    public CreateRecipeReqBodyDto(long user_id, String mealType, String recipeIngredients) {
        this.user_id = user_id;
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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
