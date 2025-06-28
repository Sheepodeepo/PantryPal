import Link from "next/link"
import { ArrowLeft } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import RecipeCardWrapper from "@/components/recipe-card-wrapper"
import { recipes } from "@/lib/data"

// Define categories with their descriptions and colors
const categories = [
  {
    name: "Breakfast",
    description: "Start your day with delicious morning meals",
    color: "bg-orange-100 text-orange-800 border-orange-200",
    count: recipes.filter((recipe) => recipe.tags.includes("Breakfast")).length,
  },
  {
    name: "Lunch",
    description: "Perfect midday meals to fuel your afternoon",
    color: "bg-green-100 text-green-800 border-green-200",
    count: recipes.filter((recipe) => recipe.tags.includes("Lunch")).length,
  },
  {
    name: "Dinner",
    description: "Satisfying evening meals for the whole family",
    color: "bg-blue-100 text-blue-800 border-blue-200",
    count: recipes.filter((recipe) => recipe.tags.includes("Dinner")).length,
  },
  {
    name: "Dessert",
    description: "Sweet treats to end your meal perfectly",
    color: "bg-pink-100 text-pink-800 border-pink-200",
    count: recipes.filter((recipe) => recipe.tags.includes("Dessert")).length,
  },
  {
    name: "Italian",
    description: "Classic Italian cuisine and flavors",
    color: "bg-red-100 text-red-800 border-red-200",
    count: recipes.filter((recipe) => recipe.tags.includes("Italian")).length,
  },
  {
    name: "Asian",
    description: "Authentic Asian dishes and cooking styles",
    color: "bg-yellow-100 text-yellow-800 border-yellow-200",
    count: recipes.filter((recipe) => recipe.tags.includes("Asian")).length,
  },
  {
    name: "Vegetarian",
    description: "Plant-based meals that are both healthy and delicious",
    color: "bg-emerald-100 text-emerald-800 border-emerald-200",
    count: recipes.filter((recipe) => recipe.tags.includes("Vegetarian")).length,
  },
  {
    name: "Quick",
    description: "Fast and easy recipes for busy schedules",
    color: "bg-purple-100 text-purple-800 border-purple-200",
    count: recipes.filter((recipe) => recipe.tags.includes("Quick")).length,
  },
  {
    name: "Healthy",
    description: "Nutritious meals that taste great",
    color: "bg-teal-100 text-teal-800 border-teal-200",
    count: recipes.filter((recipe) => recipe.tags.includes("Healthy")).length,
  },
]

export default function CategoriesPage() {
  return (
    <div className="container mx-auto px-4 py-8">
      {/* Header */}
      <div className="mb-8">
        <Link href="/" className="inline-flex items-center gap-2 mb-4 text-gray-600 hover:text-gray-900">
          <ArrowLeft className="h-4 w-4" />
          <span>Back to Home</span>
        </Link>
        <h1 className="text-3xl md:text-4xl font-bold mb-4">Recipe Categories</h1>
        <p className="text-gray-600 text-lg">Explore recipes by category to find exactly what you're looking for</p>
      </div>

      {/* Categories Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-12">
        {categories.map((category) => (
          <Link key={category.name} href={`/categories/${category.name.toLowerCase()}`} className="group block">
            <div className="bg-white rounded-lg border border-gray-200 p-6 hover:shadow-md transition-shadow h-full">
              <div className="flex items-start justify-between mb-4">
                <h3 className="text-xl font-semibold group-hover:text-primary transition-colors">{category.name}</h3>
                <Badge variant="secondary" className={category.color}>
                  {category.count} recipes
                </Badge>
              </div>
              <p className="text-gray-600 text-sm leading-relaxed">{category.description}</p>
            </div>
          </Link>
        ))}
      </div>

      {/* Popular Recipes Section */}
      <section>
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-6 gap-4">
          <div>
            <h2 className="text-2xl font-semibold mb-2">Popular Recipes</h2>
            <p className="text-gray-600">Some of our most loved recipes across all categories</p>
          </div>
          <Button variant="outline" asChild>
            <Link href="/">View All Recipes</Link>
          </Button>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {recipes.slice(0, 6).map((recipe) => (
            <RecipeCardWrapper key={recipe.id} recipe={recipe} />
          ))}
        </div>
      </section>
    </div>
  )
}
