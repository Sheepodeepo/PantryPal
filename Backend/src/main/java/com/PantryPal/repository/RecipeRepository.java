package com.PantryPal.repository;

import com.PantryPal.model.Recipe;
import org.springframework.data.repository.ListCrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RecipeRepository {
    void save(Recipe recipe) throws SQLException;
    void update(Recipe recipe);
    void deleteById(long id);
    Optional<Recipe> findById(long id); //in ListCrudRepository returns Optional<Recipe>
    Optional<Recipe> findByRecipeName(String name);
    List<Recipe> findAll();
    void deleteAll();
    void deleteByRecipeName(String name);

}
//public interface RecipeRepository extends ListCrudRepository<Recipe, Long> {
//    Optional<Recipe> findByRecipeName(String name);
//    void deleteByRecipeName(String name);
//
//}
