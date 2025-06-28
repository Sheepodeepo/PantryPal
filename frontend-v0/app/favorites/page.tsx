import Link from "next/link"
import { ArrowLeft, Heart } from "lucide-react"
import { Button } from "@/components/ui/button"
import RecipeCard from "@/components/recipe-card"
import { getCurrentUser } from "@/lib/actions/auth-actions"
import { getUserFavorites } from "@/lib/actions/favorites-actions"
import { redirect } from "next/navigation"

export default async function FavoritesPage() {
  const user = await getCurrentUser()

  if (!user) {
    redirect("/login")
  }

  const favoriteRecipes = await getUserFavorites(user.id)

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Header */}
      <div className="mb-8">
        <Link href="/" className="inline-flex items-center gap-2 mb-4 text-gray-600 hover:text-gray-900">
          <ArrowLeft className="h-4 w-4" />
          <span>Back to Home</span>
        </Link>

        <div className="flex items-center gap-3 mb-4">
          <Heart className="h-8 w-8 text-red-500 fill-current" />
          <div>
            <h1 className="text-3xl md:text-4xl font-bold">My Favorites</h1>
            <p className="text-gray-600 text-lg">Your saved recipes collection</p>
          </div>
        </div>

        {favoriteRecipes.length > 0 && (
          <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
            <p className="text-blue-800 text-sm">
              <strong>{favoriteRecipes.length}</strong> recipe{favoriteRecipes.length !== 1 ? "s" : ""} saved to your
              favorites
            </p>
          </div>
        )}
      </div>

      {/* Favorites Grid */}
      {favoriteRecipes.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {favoriteRecipes.map((recipe) => (
            <RecipeCard key={recipe.id} recipe={recipe} />
          ))}
        </div>
      ) : (
        <div className="text-center py-16">
          <Heart className="h-16 w-16 text-gray-300 mx-auto mb-4" />
          <h3 className="text-xl font-semibold mb-2">No favorites yet</h3>
          <p className="text-gray-600 mb-6 max-w-md mx-auto">
            Start exploring recipes and save your favorites by clicking the heart icon on any recipe you love!
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button asChild>
              <Link href="/">Browse Recipes</Link>
            </Button>
            <Button variant="outline" asChild>
              <Link href="/categories">Explore Categories</Link>
            </Button>
          </div>
        </div>
      )}

      {/* Tips Section */}
      {favoriteRecipes.length > 0 && (
        <section className="mt-16 pt-8 border-t">
          <h2 className="text-2xl font-semibold mb-6">Recipe Tips</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="bg-green-50 border border-green-200 rounded-lg p-6">
              <h3 className="font-semibold text-green-800 mb-2">Meal Planning</h3>
              <p className="text-green-700 text-sm">
                Use your favorite recipes to plan your weekly meals. Mix and match different cuisines for variety!
              </p>
            </div>
            <div className="bg-orange-50 border border-orange-200 rounded-lg p-6">
              <h3 className="font-semibold text-orange-800 mb-2">Shopping Lists</h3>
              <p className="text-orange-700 text-sm">
                Create shopping lists from your favorite recipes to make grocery shopping more efficient.
              </p>
            </div>
          </div>
        </section>
      )}
    </div>
  )
}
