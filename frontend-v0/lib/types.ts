export interface Recipe {
  id: string
  title: string
  description: string
  image: string
  prepTime: number
  cookTime: number
  servings: number
  difficulty: "Easy" | "Medium" | "Hard"
  tags: string[]
  ingredients: string[]
  instructions: string[]
}

export interface User {
  id: string
  name: string
  email: string
  password: string
  favorites: string[]
}
