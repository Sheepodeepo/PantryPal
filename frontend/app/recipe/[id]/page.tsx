import Image from "next/image"
import Link from "next/link"
import { ArrowLeft, Clock, Utensils, Users } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { recipes } from "@/lib/data"
import { getCurrentUser } from "@/lib/actions/auth-actions"
import { isRecipeFavorited } from "@/lib/actions/favorites-actions"
import FavoriteButton from "@/components/favorite-button"

interface RecipePageProps {
  params: {
    id: string
  }
}

export default async function RecipePage({ params }: RecipePageProps) {
  const recipe = recipes.find((r) => r.id === params.id)
  const user = await getCurrentUser()
  const isFavorited = user ? await isRecipeFavorited(params.id) : false

  if (!recipe) {
    return (
      <div className="container mx-auto px-4 py-16 text-center">
        <h1 className="text-2xl md:text-3xl font-bold mb-4">Recipe not found</h1>
        <p className="mb-8">The recipe you're looking for doesn't exist.</p>
        <Button asChild>
          <Link href="/">Back to Home</Link>
        </Button>
      </div>
    )
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <Link href="/" className="inline-flex items-center gap-2 mb-6 text-gray-600 hover:text-gray-900">
        <ArrowLeft className="h-4 w-4" />
        <span>Back to recipes</span>
      </Link>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2">
          <div className="relative h-[250px] md:h-[400px] w-full rounded-lg overflow-hidden mb-6">
            <Image src={recipe.image || "/placeholder.svg"} alt={recipe.title} fill className="object-cover" />
          </div>

          <h1 className="text-2xl md:text-3xl font-bold mb-4">{recipe.title}</h1>

          <div className="flex flex-wrap gap-2 mb-6">
            {recipe.tags.map((tag) => (
              <Badge key={tag} variant="secondary">
                {tag}
              </Badge>
            ))}
          </div>

          <p className="text-gray-700 mb-8">{recipe.description}</p>

          <div className="mb-8">
            <h2 className="text-lg md:text-xl font-semibold mb-4">Ingredients</h2>
            <ul className="list-disc pl-5 space-y-2">
              {recipe.ingredients.map((ingredient, index) => (
                <li key={index} className="text-sm md:text-base">
                  {ingredient}
                </li>
              ))}
            </ul>
          </div>

          <div>
            <h2 className="text-lg md:text-xl font-semibold mb-4">Instructions</h2>
            <ol className="list-decimal pl-5 space-y-4">
              {recipe.instructions.map((step, index) => (
                <li key={index} className="pl-2">
                  <p className="text-sm md:text-base">{step}</p>
                </li>
              ))}
            </ol>
          </div>
        </div>

        <div className="lg:col-span-1">
          <div className="bg-gray-50 rounded-lg p-4 md:p-6 sticky top-8">
            <h3 className="text-lg font-semibold mb-4">Recipe Details</h3>

            <div className="space-y-4">
              <div className="flex items-center gap-3">
                <Clock className="h-5 w-5 text-gray-500 flex-shrink-0" />
                <div>
                  <p className="text-sm text-gray-500">Prep Time</p>
                  <p className="font-medium">{recipe.prepTime} mins</p>
                </div>
              </div>

              <div className="flex items-center gap-3">
                <Clock className="h-5 w-5 text-gray-500 flex-shrink-0" />
                <div>
                  <p className="text-sm text-gray-500">Cook Time</p>
                  <p className="font-medium">{recipe.cookTime} mins</p>
                </div>
              </div>

              <div className="flex items-center gap-3">
                <Users className="h-5 w-5 text-gray-500 flex-shrink-0" />
                <div>
                  <p className="text-sm text-gray-500">Servings</p>
                  <p className="font-medium">{recipe.servings}</p>
                </div>
              </div>

              <div className="flex items-center gap-3">
                <Utensils className="h-5 w-5 text-gray-500 flex-shrink-0" />
                <div>
                  <p className="text-sm text-gray-500">Difficulty</p>
                  <p className="font-medium">{recipe.difficulty}</p>
                </div>
              </div>
            </div>

            <div className="mt-6 pt-6 border-t">
              <FavoriteButton recipeId={recipe.id} isFavorited={isFavorited} user={user} />
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
