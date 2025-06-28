"use server"

import { cookies } from "next/headers"
import { redirect } from "next/navigation"
import { users } from "@/lib/data"

// In a real app, you would use a database and proper password hashing
// This is a simplified example for demonstration purposes

type LoginData = {
  email: string
  password: string
}

type SignupData = {
  name: string
  email: string
  password: string
}

type AuthResult = {
  success: boolean
  error?: string
}

// Simple in-memory user storage for demo purposes
// In a real app, you would use a database
const registeredUsers = [...users]

export async function login(data: LoginData): Promise<AuthResult> {
  // Simulate server delay
  await new Promise((resolve) => setTimeout(resolve, 1000))

  const user = registeredUsers.find((u) => u.email === data.email)

  if (!user) {
    return { success: false, error: "User not found" }
  }

  if (user.password !== data.password) {
    return { success: false, error: "Invalid password" }
  }

  // Set auth cookie
  const cookieStore = cookies()
  cookieStore.set(
    "auth-token",
    JSON.stringify({
      id: user.id,
      name: user.name,
      email: user.email,
    }),
    {
      httpOnly: true,
      secure: process.env.NODE_ENV === "production",
      maxAge: 60 * 60 * 24 * 7, // 1 week
      path: "/",
    },
  )

  return { success: true }
}

export async function signup(data: SignupData): Promise<AuthResult> {
  // Simulate server delay
  await new Promise((resolve) => setTimeout(resolve, 1000))

  // Check if user already exists
  if (registeredUsers.some((u) => u.email === data.email)) {
    return { success: false, error: "Email already in use" }
  }

  // Create new user
  const newUser = {
    id: `user-${Date.now()}`,
    name: data.name,
    email: data.email,
    password: data.password, // In a real app, you would hash this
    favorites: [],
  }

  registeredUsers.push(newUser)

  // Set auth cookie
  const cookieStore = cookies()
  cookieStore.set(
    "auth-token",
    JSON.stringify({
      id: newUser.id,
      name: newUser.name,
      email: newUser.email,
    }),
    {
      httpOnly: true,
      secure: process.env.NODE_ENV === "production",
      maxAge: 60 * 60 * 24 * 7, // 1 week
      path: "/",
    },
  )

  return { success: true }
}

export async function logout() {
  const cookieStore = cookies()
  cookieStore.delete("auth-token")
  redirect("/")
}

export async function getCurrentUser() {
  const cookieStore = cookies()
  const authCookie = cookieStore.get("auth-token")

  if (!authCookie) {
    return null
  }

  try {
    return JSON.parse(authCookie.value)
  } catch (error) {
    return null
  }
}
