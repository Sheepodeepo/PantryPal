import Image from "next/image"
import Link from "next/link"
import { Clock, Utensils } from "lucide-react"
import { Card, CardContent } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import type { Recipe } from "@/lib/types"

interface RecipeCardProps {
  recipe: Recipe
}

export default function RecipeCard({ recipe }: RecipeCardProps) {
  return (
    <Link href={`/recipe/${recipe.id}`}>
      <Card className="overflow-hidden h-full hover:shadow-md transition-shadow">
        <div className="relative h-48 w-full">
          <Image src={recipe.image || "/placeholder.svg"} alt={recipe.title} fill className="object-cover" />
        </div>
        <CardContent className="p-4">
          <div className="flex gap-2 mb-2">
            {recipe.tags.map((tag) => (
              <Badge key={tag} variant="secondary" className="text-xs">
                {tag}
              </Badge>
            ))}
          </div>
          <h3 className="font-semibold text-xl mb-2">{recipe.title}</h3>
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
        </CardContent>
      </Card>
    </Link>
  )
}
