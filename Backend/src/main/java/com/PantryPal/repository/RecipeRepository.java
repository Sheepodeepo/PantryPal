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
    Recipe findById(long id); //in ListCrudRepository returns Optional<Recipe>
    List<Recipe> findAll();
    void deleteAll();

}
//public interface RecipeRepository extends ListCrudRepository<Recipe, Long> {
//    Optional<Recipe> findByRecipeName(String name);
//    void deleteByRecipeName(String name);
//
//}
