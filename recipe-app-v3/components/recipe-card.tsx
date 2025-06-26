import Image from "next/image"
import Link from "next/link"
import { Clock, Utensils } from "lucide-react"
import { Badge } from "@/components/ui/badge"
import type { Recipe } from "@/lib/types"
import FavoriteButton from "@/components/favorite-button"

interface RecipeCardProps {
  recipe: Recipe
  user?: {
    id: string
    name: string
    email: string
  } | null
  isFavorited?: boolean
}

export default function RecipeCard({ recipe, user, isFavorited = false }: RecipeCardProps) {
  return (
    <div className="overflow-hidden h-full hover:shadow-md transition-shadow bg-white rounded-lg border border-gray-200">
      <div className="relative">
        <Link href={`/recipe/${recipe.id}`}>
          <div className="relative h-48 w-full">
            <Image src={recipe.image || "/placeholder.svg"} alt={recipe.title} fill className="object-cover" />
          </div>
        </Link>
        {/* Favorite button positioned over the image */}
        <div className="absolute top-2 right-2">
          <FavoriteButton recipeId={recipe.id} isFavorited={isFavorited} user={user} variant="icon" />
        </div>
      </div>
      <div className="p-4">
        <div className="flex gap-2 mb-2">
          {recipe.tags.map((tag) => (
            <Badge key={tag} variant="secondary" className="text-xs">
              {tag}
            </Badge>
          ))}
        </div>
        <Link href={`/recipe/${recipe.id}`}>
          <h3 className="font-semibold text-xl mb-2 hover:text-primary transition-colors">{recipe.title}</h3>
        </Link>
        <p className="text-gray-600 text-sm line-clamp-2 mb-4">{recipe.description}</p>
        <div className="flex items-center gap-4 text-sm text-gray-500">
          <div className="flex items-center gap-1">
            <Clock className="h-4 w-4" />
            <span>{recipe.cookTime} mins</span>
          </div>
          <div className="flex items-center gap-1">
            <Utensils className="h-4 w-4" />
            <span>{recipe.difficulty}</span>
          </div>
        </div>
      </div>
    </div>
  )
}
