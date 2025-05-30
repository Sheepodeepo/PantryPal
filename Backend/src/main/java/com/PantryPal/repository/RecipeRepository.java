package com.PantryPal.repository;

import com.PantryPal.model.Recipe;
import com.PantryPal.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//public interface RecipeRepository {
//    void save(Recipe recipe) throws SQLException;
//    void update(Recipe recipe);
//    void deleteById(long id);
//    Optional<Recipe> findById(long id); //in ListCrudRepository returns Optional<Recipe>
//    Optional<Recipe> findByRecipeName(String name);
//    List<Recipe> findAll();
//    void deleteAll();
//    void deleteByRecipeName(String name);
//
//}
public interface RecipeRepository extends ListCrudRepository<Recipe, Long> {
//    @Query("SELECT id, user_id, name, meal_type, ingredients, instructions, created_date, updated_date FROM recipe WHERE user_id = :user_id")
    List<Recipe> findAllByUserId(long userId);

//     Equivalent @Query annotation using JPQL
//    @Query("SELECT r FROM Recipe r WHERE r.userId = :userId")
//    List<Recipe> findRecipesByUserJPQL(long userId);
}
