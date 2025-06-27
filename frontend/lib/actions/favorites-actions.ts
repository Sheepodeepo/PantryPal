"use server"
import { recipes } from "@/lib/data"
import { getCurrentUser } from "./auth-actions"

// In a real app, this would be stored in a database
// For demo purposes, we'll use a simple in-memory store
const userFavorites: { [userId: string]: string[] } = {
  "user-1": ["1", "3"],
  "user-2": ["2", "5"],
}

export async function getUserFavorites(userId: string) {
  // Get user's favorite recipe IDs
  const favoriteIds = userFavorites[userId] || []

  // Return the actual recipe objects
  return recipes.filter((recipe) => favoriteIds.includes(recipe.id))
}

export async function addToFavorites(recipeId: string) {
  const user = await getCurrentUser()

  if (!user) {
    return { success: false, error: "User not authenticated" }
  }

  // Initialize user favorites if they don't exist
  if (!userFavorites[user.id]) {
    userFavorites[user.id] = []
  }

  // Check if recipe is already in favorites
  if (userFavorites[user.id].includes(recipeId)) {
    return { success: false, error: "Recipe already in favorites" }
  }

  // Add recipe to favorites
  userFavorites[user.id].push(recipeId)

  return { success: true, message: "Recipe added to favorites" }
}

export async function removeFromFavorites(recipeId: string) {
  const user = await getCurrentUser()

  if (!user) {
    return { success: false, error: "User not authenticated" }
  }

  // Initialize user favorites if they don't exist
  if (!userFavorites[user.id]) {
    userFavorites[user.id] = []
  }

  // Remove recipe from favorites
  userFavorites[user.id] = userFavorites[user.id].filter((id) => id !== recipeId)

  return { success: true, message: "Recipe removed from favorites" }
}

export async function isRecipeFavorited(recipeId: string) {
  const user = await getCurrentUser()

  if (!user) {
    return false
  }

  return userFavorites[user.id]?.includes(recipeId) || false
}

export async function toggleFavorite(recipeId: string) {
  const user = await getCurrentUser()

  if (!user) {
    return { success: false, error: "User not authenticated" }
  }

  const isFavorited = await isRecipeFavorited(recipeId)

  if (isFavorited) {
    return await removeFromFavorites(recipeId)
  } else {
    return await addToFavorites(recipeId)
  }
}
