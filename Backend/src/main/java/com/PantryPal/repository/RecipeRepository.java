package com.PantryPal.repository;

import com.PantryPal.model.Recipe;
import com.PantryPal.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
public interface RecipeRepository extends ListCrudRepository<Recipe, Long> {
    List<Recipe> findAllByUserId(long userId);
}
