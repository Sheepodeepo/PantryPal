package com.PantryPal.repository;

import com.PantryPal.model.Recipe;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteRepository {

    private JdbcClient jdbcClient;

    public FavoriteRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public boolean isFavoriteRecipe(Long userId, Long recipeId){
        return jdbcClient.sql("SELECT 1 FROM favorites WHERE user_id = ? AND recipe_id = ?")
                .param(userId)
                .param(recipeId)
                .query(Long.class)
                .optional().isPresent();
    }

    public void save(Long userId, Long recipeId){
        //Update Favorites Table
        String sql = "Insert INTO favorites(user_id, recipe_id) VALUES (?, ?)";
        jdbcClient.sql(sql)
                .param(userId)
                .param(recipeId)
                .update();

        //Update Recipes Table
        jdbcClient.sql("UPDATE recipes SET favorite_count = favorite_count + 1 WHERE id = ?")
                .param(recipeId)
                .update();
    }

    //Remove a Favorite recipe
    public void deleteByUserIdAndRecipeId(Long userId, Long recipeId){
        String sql = "DELETE FROM favorites WHERE user_id = ? AND recipe_id = ?";
        jdbcClient.sql(sql)
                .param(userId)
                .param(recipeId)
                .update();

        //Update Recipes Table
        jdbcClient.sql("UPDATE recipes SET favorite_count = favorite_count - 1 WHERE id = ?")
                .param(recipeId)
                .update();
    }

    // Get Top Favorite Recipes --> Refactor into RecipeRepository pls.
        public List<Recipe> findTopFavoriteRecipes(int limit) {
            return jdbcClient.sql("SELECT * FROM recipes ORDER BY favorite_count DESC LIMIT ?")
                    .param(limit)
                    .query(Recipe.class)
                    .list();
        }

    public List<Recipe> findFavoriteRecipesByUser(Long userId){
        String sql = """
                SELECT r.* FROM recipes r
                JOIN favorites f ON r.id=f.recipe_id
                WHERE f.user_id = ?
                """;
        return jdbcClient.sql(sql)
                .param(userId)
                .query(Recipe.class)
                .list();
    }

}

