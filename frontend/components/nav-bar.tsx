"use client"
import Link from "next/link"
import { Menu } from "lucide-react"
import { Button } from "@/components/ui/button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import UserMenu from "@/components/auth/user-menu"
import AuthButtons from "@/components/auth/auth-buttons"

interface NavBarProps {
  user?: {
    id: string
    name: string
    email: string
  } | null
}

export default function NavBar({ user }: NavBarProps) {
  return (
    <nav className="border-b">
      <div className="container mx-auto px-4 py-4">
        <div className="flex justify-between items-center">
          {/* Logo */}
          <Link href="/" className="text-xl font-bold">
            PantryPal
          </Link>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center gap-6">
            <Link href="/categories" className="text-gray-600 hover:text-gray-900 transition-colors">
              Categories
            </Link>
            <Link href="/favorites" className="text-gray-600 hover:text-gray-900 transition-colors">
              Favorites
            </Link>
            {user ? (
              <div className="flex items-center gap-4">
                <Button asChild>
                  <Link href="/recipes/new">Add Recipe</Link>
                </Button>
                <UserMenu user={user} />
              </div>
            ) : (
              <AuthButtons />
            )}
          </div>

          {/* Mobile Navigation - Dropdown Menu */}
          <div className="md:hidden">
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon" className="h-10 w-10">
                  <Menu className="h-5 w-5" />
                  <span className="sr-only">Toggle menu</span>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end" className="w-56">
                {/* User Info Section - Only show if logged in */}
                {user && (
                  <>
                    <div className="px-2 py-2">
                      <div className="flex items-center space-x-3">
                        <div className="w-8 h-8 bg-primary text-primary-foreground rounded-full flex items-center justify-center text-sm font-medium">
                          {user.name
                            .split(" ")
                            .map((n) => n[0])
                            .join("")
                            .toUpperCase()
                            .substring(0, 2)}
                        </div>
                        <div className="flex-1 min-w-0">
                          <p className="text-sm font-medium truncate">{user.name}</p>
                          <p className="text-xs text-gray-500 truncate">{user.email}</p>
                        </div>
                      </div>
                    </div>
                    <DropdownMenuSeparator />
                  </>
                )}

                {/* Navigation Links */}
                <DropdownMenuItem asChild>
                  <Link href="/categories" className="cursor-pointer">
                    Categories
                  </Link>
                </DropdownMenuItem>
                <DropdownMenuItem asChild>
                  <Link href="/favorites" className="cursor-pointer">
                    Favorites
                  </Link>
                </DropdownMenuItem>

                {/* User-specific links */}
                {user && (
                  <>
                    <DropdownMenuItem asChild>
                      <Link href="/profile" className="cursor-pointer">
                        Profile
                      </Link>
                    </DropdownMenuItem>
                    <DropdownMenuItem asChild>
                      <Link href="/recipes/new" className="cursor-pointer">
                        Add Recipe
                      </Link>
                    </DropdownMenuItem>
                  </>
                )}

                <DropdownMenuSeparator />

                {/* Auth Section */}
                {user ? (
                  <DropdownMenuItem asChild>
                    <form action="/api/auth/logout" method="POST" className="w-full">
                      <button type="submit" className="w-full text-left text-red-600 hover:text-red-700">
                        Logout
                      </button>
                    </form>
                  </DropdownMenuItem>
                ) : (
                  <>
                    <DropdownMenuItem asChild>
                      <Link href="/login" className="cursor-pointer">
                        Login
                      </Link>
                    </DropdownMenuItem>
                    <DropdownMenuItem asChild>
                      <Link href="/signup" className="cursor-pointer">
                        Sign Up
                      </Link>
                    </DropdownMenuItem>
                  </>
                )}
              </DropdownMenuContent>
            </DropdownMenu>
          </div>
        </div>
      </div>
    </nav>
  )
}
