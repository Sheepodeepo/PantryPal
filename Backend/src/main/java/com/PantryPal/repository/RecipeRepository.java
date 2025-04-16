package com.PantryPal.repository;

import com.PantryPal.model.Recipe;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface RecipeRepository extends ListCrudRepository<Recipe, Long> {
    Optional<Recipe> findByRecipeName(String name);
    void deleteByRecipeName(String name);

}
