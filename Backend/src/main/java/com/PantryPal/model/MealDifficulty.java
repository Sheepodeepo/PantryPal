package com.PantryPal.model;

public enum MealDifficulty {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");

    private final String difficulty;

    MealDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDisplayName() {
        return difficulty;
    }
}
