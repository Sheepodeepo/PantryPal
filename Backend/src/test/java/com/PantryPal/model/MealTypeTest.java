package com.PantryPal.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MealTypeTest {

    private final String BREAKFAST = "BREAKFAST";
    private final String LUNCH = "LUNCH";
    private final String DINNER = "DINNER";
    private final String SNACK = "SNACK";

    @Test
    void getDisplayName() {
        assertEquals("BREAKFAST", MealType.BREAKFAST.getDisplayName());
        assertEquals("LUNCH", MealType.LUNCH.getDisplayName());
        assertEquals("DINNER", MealType.DINNER.getDisplayName());
        assertEquals("SNACK", MealType.SNACK.getDisplayName());

    }

    @Test
    void valueOf() {
        assertEquals(MealType.BREAKFAST, MealType.valueOf(BREAKFAST));
        assertEquals(MealType.LUNCH, MealType.valueOf(LUNCH));
        assertEquals(MealType.DINNER, MealType.valueOf(DINNER));
        assertEquals(MealType.SNACK, MealType.valueOf(SNACK));

    }
}