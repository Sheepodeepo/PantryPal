package com.PantryPal.model;

/**Enums class to display 4 options of Meal Types:
 * Breakfast, Lunch, Dinner, Snack
 *
 */
public enum MealType {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    SNACK("Snack");

    private final String mealTypeName;

    MealType(String mealType) {
        this.mealTypeName = mealType;
    }

    public String getDisplayName() {
        return mealTypeName;
    }

}
