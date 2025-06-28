import Link from "next/link"
import { Search } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import RecipeCardWrapper from "@/components/recipe-card-wrapper"
import { recipes } from "@/lib/data"

export default function Home() {
  return (
    <div className="container mx-auto px-4 py-8">
      <header className="mb-8">
        <h1 className="text-3xl md:text-4xl font-bold text-center mb-6">Delicious Recipes</h1>
        <div className="max-w-md mx-auto relative">
          <Input type="text" placeholder="Search recipes..." className="pl-10" />
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
        </div>
      </header>

      <section className="mb-12">
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-6 gap-4">
          <h2 className="text-xl md:text-2xl font-semibold">Featured Recipes</h2>
          <Button variant="outline" size="sm">
            View All
          </Button>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {recipes.slice(0, 3).map((recipe) => (
            <RecipeCardWrapper key={recipe.id} recipe={recipe} />
          ))}
        </div>
      </section>

      <section className="mb-12">
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-6 gap-4">
          <h2 className="text-xl md:text-2xl font-semibold">Popular Categories</h2>
          <Button variant="outline" size="sm">
            View All
          </Button>
        </div>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          {["Breakfast", "Lunch", "Dinner", "Dessert"].map((category) => (
            <Link
              key={category}
              href={`/category/${category.toLowerCase()}`}
              className="bg-gray-100 hover:bg-gray-200 transition-colors rounded-lg p-4 md:p-6 text-center"
            >
              <h3 className="font-medium text-sm md:text-lg">{category}</h3>
            </Link>
          ))}
        </div>
      </section>

      <section>
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-6 gap-4">
          <h2 className="text-xl md:text-2xl font-semibold">Latest Recipes</h2>
          <Button variant="outline" size="sm">
            View All
          </Button>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {recipes.slice(3, 6).map((recipe) => (
            <RecipeCardWrapper key={recipe.id} recipe={recipe} />
          ))}
        </div>
      </section>
    </div>
  )
}
