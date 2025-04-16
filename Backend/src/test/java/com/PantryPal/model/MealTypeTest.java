package com.PantryPal.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MealTypeTest {

    private final String BREAKFAST = "BREAKFAST";
    private final String LUNCH = "LUNCH";
    private final String DINNER = "DINNER";
    private final String SNACK = "SNACK";

    @Test
    void getDisplayName() {
        assertEquals("Breakfast", MealType.BREAKFAST.getDisplayName());
        assertEquals("Lunch", MealType.LUNCH.getDisplayName());
        assertEquals("Dinner", MealType.DINNER.getDisplayName());
        assertEquals("Snack", MealType.SNACK.getDisplayName());

    }

    @Test
    void valueOf() {
        assertEquals(MealType.BREAKFAST, MealType.valueOf(BREAKFAST));
        assertEquals(MealType.LUNCH, MealType.valueOf(LUNCH));
        assertEquals(MealType.DINNER, MealType.valueOf(DINNER));
        assertEquals(MealType.SNACK, MealType.valueOf(SNACK));

    }
}