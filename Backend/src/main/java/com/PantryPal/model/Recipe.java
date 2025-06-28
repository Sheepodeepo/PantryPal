package com.PantryPal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("recipes")
public class Recipe {
    @Id
    private long id;
    private long userId;
    private String name;
    private MealType mealType;
    private String ingredients;
    private String instructions;
    private long favorite_count;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    public Recipe() {
    }

    public Recipe(long userId, String name, MealType mealType, String ingredients, String instructions) {
        this.userId = userId;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.mealType = mealType;
        this.name = name;
        favorite_count = 0;
        this.createdDate = LocalDate.now();
    }

    public enum Difficulty{
        EASY, MEDIUM, HARD
    }

    public long getId() {
        return id;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public long getUserId() {
        return userId;
    }

    public long getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(long favorite_count) {
        this.favorite_count = favorite_count;
    }
}
