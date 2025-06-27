import Link from "next/link"
import { ArrowLeft } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import RecipeCardWrapper from "@/components/recipe-card-wrapper"
import { recipes } from "@/lib/data"
import { notFound } from "next/navigation"

interface CategoryPageProps {
  params: {
    category: string
  }
}

// Valid categories
const validCategories = [
  "breakfast",
  "lunch",
  "dinner",
  "dessert",
  "italian",
  "asian",
  "vegetarian",
  "quick",
  "healthy",
]

export default function CategoryPage({ params }: CategoryPageProps) {
  const categoryName = params.category.toLowerCase()

  // Check if category is valid
  if (!validCategories.includes(categoryName)) {
    notFound()
  }

  // Capitalize first letter for display
  const displayCategory = categoryName.charAt(0).toUpperCase() + categoryName.slice(1)

  // Filter recipes by category
  const categoryRecipes = recipes.filter((recipe) => recipe.tags.some((tag) => tag.toLowerCase() === categoryName))

  // Get category description
  const getCategoryDescription = (category: string) => {
    const descriptions: { [key: string]: string } = {
      breakfast: "Start your day with these delicious morning meals",
      lunch: "Perfect midday meals to fuel your afternoon",
      dinner: "Satisfying evening meals for the whole family",
      dessert: "Sweet treats to end your meal perfectly",
      italian: "Classic Italian cuisine and authentic flavors",
      asian: "Authentic Asian dishes and traditional cooking styles",
      vegetarian: "Plant-based meals that are both healthy and delicious",
      quick: "Fast and easy recipes perfect for busy schedules",
      healthy: "Nutritious meals that don't compromise on taste",
    }
    return descriptions[category] || "Discover amazing recipes in this category"
  }

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Header */}
      <div className="mb-8">
        <Link href="/categories" className="inline-flex items-center gap-2 mb-4 text-gray-600 hover:text-gray-900">
          <ArrowLeft className="h-4 w-4" />
          <span>Back to Categories</span>
        </Link>

        <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 mb-4">
          <div>
            <h1 className="text-3xl md:text-4xl font-bold mb-2">{displayCategory} Recipes</h1>
            <p className="text-gray-600 text-lg">{getCategoryDescription(categoryName)}</p>
          </div>
          <Badge variant="secondary" className="text-sm w-fit">
            {categoryRecipes.length} recipes found
          </Badge>
        </div>
      </div>

      {/* Recipes Grid */}
      {categoryRecipes.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {categoryRecipes.map((recipe) => (
            <RecipeCardWrapper key={recipe.id} recipe={recipe} />
          ))}
        </div>
      ) : (
        <div className="text-center py-16">
          <h3 className="text-xl font-semibold mb-2">No recipes found</h3>
          <p className="text-gray-600 mb-6">
            We don't have any {displayCategory.toLowerCase()} recipes yet, but check back soon!
          </p>
          <Button asChild>
            <Link href="/categories">Browse Other Categories</Link>
          </Button>
        </div>
      )}

      {/* Related Categories */}
      {categoryRecipes.length > 0 && (
        <section className="mt-16 pt-8 border-t">
          <h2 className="text-2xl font-semibold mb-6">Explore More Categories</h2>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            {validCategories
              .filter((cat) => cat !== categoryName)
              .slice(0, 4)
              .map((category) => (
                <Link
                  key={category}
                  href={`/categories/${category}`}
                  className="bg-gray-100 hover:bg-gray-200 transition-colors rounded-lg p-4 text-center"
                >
                  <h3 className="font-medium text-sm md:text-base capitalize">{category}</h3>
                </Link>
              ))}
          </div>
        </section>
      )}
    </div>
  )
}
