package com.PantryPal.service;

import com.PantryPal.model.Recipe;
import com.PantryPal.repository.FavoriteRepository;
import com.PantryPal.repository.RecipeRepository;
import com.PantryPal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final FavoriteRepository favoriteRepository;

    public FavoriteService(UserRepository userRepository, RecipeRepository recipeRepository, FavoriteRepository favoriteImplRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.favoriteRepository = favoriteImplRepository;
    }

    public void addFavorite(Long userId, Long recipeId){
        if (favoriteRepository.isFavoriteRecipe(userId, recipeId)) {
            return; // Already exists
        }

        favoriteRepository.save(userId, recipeId);
    }

    public List<Recipe> getUserFavorites(Long userId){
        return  favoriteRepository.findFavoriteRecipesByUser(userId);
    }

    public void removeFavorite(Long userId, Long recipeId){
        if (!favoriteRepository.isFavoriteRecipe(userId, recipeId)) {
            return; // Already exists
        }
        favoriteRepository.deleteByUserIdAndRecipeId(userId,recipeId);
    }

    public List<Recipe> getTopFavoriteRecipes(int limit){
        return favoriteRepository.findTopFavoriteRecipes(limit);
    }
}
