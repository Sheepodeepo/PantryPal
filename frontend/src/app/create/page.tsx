"use client"

import type React from "react"
import { useState } from "react"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/ui/card"
import { Label } from "@/ui/label"
import { Select, SelectContent, SelectItem, SelectGroup, SelectTrigger, SelectValue } from "@/ui/select"
import { Textarea } from "@/ui/textarea"
import { LuChefHat } from "react-icons/lu" //LuClock, LuUsers 
import { Button } from "@/ui/button"
import { useRouter } from "next/navigation"

export default function GenerateRecipeForm() {
  const router = useRouter();
  const [ingredients, setIngredients] = useState("")
  const [mealType, setMealType] = useState("")
  const [isGenerating, setIsGenerating] = useState(false)
  const [recipe, setRecipe] = useState<{
    name: string
    // description: string
    // prepTime: string
    // servings: string
    mealType : string 
    ingredients: string[]
    instructions: string[]
  } | null>(null)
  const apiBaseUrl = process.env.NEXT_PUBLIC_API_URL;


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!ingredients.trim() || !mealType) return

    setIsGenerating(true)
    
    const reqBody = {
      mealType,
      ingredients
    }


    try {
      const res = await fetch(`${apiBaseUrl}/api/v1/recipe`,{
        method : "POST",
        credentials : "include",
        headers: {
          'Content-Type': 'application/json',
        },
        body : JSON.stringify(reqBody)
      });
      const data = await res.json();
      console.log(data);
      const newRecipe = {
        name : data.name,
        mealType : data.mealType,
        ingredients : data.ingredients.split(","),
        instructions : data.instructions.split("*")
      }
      console.log(newRecipe);
      setRecipe(newRecipe);
    } catch (error) {
      console.log("Internal Server Error");
      console.log(error);

    }
    setIsGenerating(false)
  }

  const handleReturn = () => {
    router.push("/");
  }

  return (
    <div className="min-h-screen">
      <div className="mx-auto max-w-4xl py-6">
        <div className="text-center">
          <h1 className="text-4xl font-bold text-gray-900 mb-2"> Generate a Recipe</h1>
          <p className="text-gray-600">Tell us what ingredients you have and what meal you want to make!</p>
        </div>

        <div className="grid gap-6 md:grid-cols-2 py-4">
          {/* Input Form */}
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <LuChefHat className="h-5 w-5" />
                Create Your Recipe
              </CardTitle>
              <CardDescription>Enter your available ingredients and choose your meal type</CardDescription>
            </CardHeader>
            <form onSubmit={handleSubmit}>
              <CardContent className="space-y-4">
                <div className="space-y-2">
                  <p >Available Ingredients </p>
                  <Textarea 
                    placeholder="Enter ingredients separated by commas (e.g., chicken, rice, broccoli, garlic, onion)"
                    value={ingredients}
                    onChange={(e) => setIngredients(e.target.value)}
                  />
                </div>
                <div className="space-y-2">
                <Label htmlFor="email">Meal Type</Label>
                  <Select value={mealType} onValueChange={setMealType} required>
                    <SelectTrigger className="w-full">
                      <SelectValue placeholder="Select meal type" />
                    </SelectTrigger>
                    <SelectContent className="bg-white">
                      <SelectGroup>
                        <SelectItem value="Breakfast">Breakfast</SelectItem>
                        <SelectItem value="Lunch">Lunch</SelectItem>
                        <SelectItem value="Dinner">Dinner</SelectItem>
                        <SelectItem value="Snack">Snack</SelectItem>
                        {/* <SelectItem value="Dessert">Dessert</SelectItem> */}
                      </SelectGroup>
                    </SelectContent>
                  </Select> 
                </div>
              </CardContent>
              <CardFooter className="flex gap-2">
                <Button type="submit" disabled={isGenerating || !ingredients.trim() || !mealType} className="flex-1">
                  {isGenerating ? "Generating Recipe..." : "Generate Another Recipe"}
                </Button>
                {recipe && (
                  <Button type="button" variant="outline" onClick={handleReturn}>
                    Save and Return
                  </Button>
                )}
              </CardFooter>
            </form>
          </Card>

          {/* Generated Recipe */}
          <Card className={recipe ? "opacity-100" : "opacity-50"}>
            <CardHeader>
              <CardTitle>Your Generated Recipe</CardTitle>
              <CardDescription>
                {recipe ? "Here's your custom recipe!" : "Your recipe will appear here"}
              </CardDescription>
            </CardHeader>
            <CardContent>
              {isGenerating ? (
                <div className="flex items-center justify-center py-8">
                  <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-orange-500"></div>
                  <span className="ml-2">Generating your recipe...</span>
                </div>
              ) : recipe ? (
                <div className="space-y-4">
                  <div>
                    <h3 className="text-xl font-semibold text-gray-900">{recipe.name}</h3>
                    {/* <p className="text-gray-600 mt-1">{recipe.description}</p> */}
                  </div>

                  {/* <div className="flex gap-4 text-sm text-gray-500">
                    <div className="flex items-center gap-1">
                      <LuClock className="h-4 w-4" />
                      {recipe.prepTime}
                    </div>
                    <div className="flex items-center gap-1">
                      <LuUsers className="h-4 w-4" />
                      Serves {recipe.servings}
                    </div>
                  </div> */}

                  <div>
                    <h4 className="font-medium text-gray-900 mb-2">Ingredients:</h4>
                    <ul className="list-disc list-inside space-y-1 text-sm text-gray-600">
                      {recipe.ingredients.map((ingredient, index) => (
                        <li key={index}>{ingredient}</li>
                      ))}
                    </ul>
                  </div>

                  <div>
                    <h4 className="font-medium text-gray-900 mb-2">Instructions:</h4>
                    <ol className="list-decimal list-inside space-y-2 text-sm text-gray-600">
                      {recipe.instructions.map((instruction, index) => (
                        <li key={index}>{instruction}</li>
                      ))}
                    </ol>
                  </div>
                </div>
              ) : (
                <div className="text-center py-8 text-gray-400">
                  <LuChefHat className="h-12 w-12 mx-auto mb-2 opacity-50" />
                  <p>Fill out the form and click Generate Recipe to see your custom recipe here</p> 
                  {/* "Generate Recipe" */}
                </div>
              )}
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  )
}
