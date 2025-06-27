package com.PantryPal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("favorites")
public class Favorite {
    private long user_id;
    private long recipe_id;

    public Favorite(){}

    public Favorite(Long user_id, Long recipe_id) {
        this.user_id = user_id;
        this.recipe_id = recipe_id;
    }

    public long getRecipe_id() {
        return recipe_id;
    }

    public long getUser_id() {
        return user_id;
    }

}