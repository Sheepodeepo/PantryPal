import { getCurrentUser } from "@/lib/actions/auth-actions"
import { isRecipeFavorited } from "@/lib/actions/favorites-actions"
import RecipeCard from "@/components/recipe-card"
import type { Recipe } from "@/lib/types"

interface RecipeCardWrapperProps {
  recipe: Recipe
}

export default async function RecipeCardWrapper({ recipe }: RecipeCardWrapperProps) {
  const user = await getCurrentUser()
  const isFavorited = user ? await isRecipeFavorited(recipe.id) : false

  return <RecipeCard recipe={recipe} user={user} isFavorited={isFavorited} />
}
