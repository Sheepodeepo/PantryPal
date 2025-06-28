"use client"

import { useState, useTransition } from "react"
import { Heart } from "lucide-react"
import { Button } from "@/components/ui/button"
import { toggleFavorite } from "@/lib/actions/favorites-actions"
import { useRouter } from "next/navigation"

interface FavoriteButtonProps {
  recipeId: string
  isFavorited: boolean
  user?: {
    id: string
    name: string
    email: string
  } | null
  variant?: "default" | "icon"
}

export default function FavoriteButton({ recipeId, isFavorited, user, variant = "default" }: FavoriteButtonProps) {
  const [favorited, setFavorited] = useState(isFavorited)
  const [isPending, startTransition] = useTransition()
  const router = useRouter()

  const handleToggleFavorite = () => {
    if (!user) {
      router.push("/login")
      return
    }

    startTransition(async () => {
      try {
        const result = await toggleFavorite(recipeId)
        if (result.success) {
          setFavorited(!favorited)
        }
      } catch (error) {
        console.error("Error toggling favorite:", error)
      }
    })
  }

  if (variant === "icon") {
    return (
      <Button
        variant="ghost"
        size="icon"
        onClick={handleToggleFavorite}
        disabled={isPending}
        className={`h-8 w-8 rounded-full bg-white/80 backdrop-blur-sm hover:bg-white/90 ${
          favorited ? "text-red-500" : "text-gray-600 hover:text-red-500"
        }`}
      >
        <Heart className={`h-4 w-4 ${favorited ? "fill-current" : ""}`} />
        <span className="sr-only">{favorited ? "Remove from favorites" : "Add to favorites"}</span>
      </Button>
    )
  }

  return (
    <Button
      variant={favorited ? "default" : "outline"}
      size="sm"
      onClick={handleToggleFavorite}
      disabled={isPending}
      className={`w-full flex items-center gap-2 ${
        favorited
          ? "bg-red-500 hover:bg-red-600 text-white border-red-500"
          : "hover:bg-red-50 hover:text-red-600 hover:border-red-300"
      }`}
    >
      <Heart className={`h-4 w-4 ${favorited ? "fill-current" : ""}`} />
      <span>{isPending ? "..." : favorited ? "Favorited" : "Add to Favorites"}</span>
    </Button>
  )
}
