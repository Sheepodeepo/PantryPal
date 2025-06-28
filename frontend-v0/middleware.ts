import { NextResponse } from "next/server"
import type { NextRequest } from "next/server"

// This function can be marked `async` if using `await` inside
export function middleware(request: NextRequest) {
  const authToken = request.cookies.get("auth-token")
  const path = request.nextUrl.pathname

  // Protected routes that require authentication
  const protectedRoutes = ["/profile", "/favorites/add", "/recipes/new"]

  // Check if the path is a protected route and user is not authenticated
  if (protectedRoutes.some((route) => path.startsWith(route)) && !authToken) {
    return NextResponse.redirect(new URL("/login", request.url))
  }

  // If user is already logged in, redirect from login/signup pages to home
  if ((path === "/login" || path === "/signup") && authToken) {
    return NextResponse.redirect(new URL("/", request.url))
  }

  return NextResponse.next()
}

// See "Matching Paths" below to learn more
export const config = {
  matcher: ["/profile/:path*", "/favorites/add/:path*", "/recipes/new/:path*", "/login", "/signup"],
}
