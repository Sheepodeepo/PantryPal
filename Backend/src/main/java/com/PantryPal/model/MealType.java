package com.PantryPal.model;

/**Enums class to display 4 options of Meal Types:
 * Breakfast, Lunch, Dinner, Snack
 *
 */
public enum MealType {
    BREAKFAST("BREAKFAST"),
    LUNCH("LUNCH"),
    DINNER("DINNER"),
    SNACK("SNACK");

    private final String mealTypeName;

    MealType(String mealType) {
        this.mealTypeName = mealType;
    }

    public String getDisplayName() {
        return mealTypeName;
    }

}
