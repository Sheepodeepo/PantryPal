"use server"

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
export async function login(data: LoginData): Promise<AuthResult> {
  try{
    const res = await fetch("http://localhost:8080/api/v1/auth/login",{
      method : "POST",
      headers: {
        'Content-Type': 'application/json',
      },
      body : JSON.stringify(data),
      credentials : "include"      
    })
    if(res.status == 401){
      return {success : false, error : "Email and/or password is incorrect"};
    }
  }
  catch(error){
    console.log("Login: Internal Servor Error Debug Message");
    console.log(error);
    return {success : false, error : "Internal Server Error. Please try again later."};
  }
  return {success : true};
}

export async function signup(data: SignupData): Promise<AuthResult> {
  try{
    const res = await fetch("http://localhost:8080/api/v1/auth/signup",{
      method : "POST",
      headers: {
        'Content-Type': 'application/json',
      },
      body : JSON.stringify(data)
    })
    if(res.status == 409){
      return {success : false, error : "User with email already exists."};
    }
  }
  catch(error){
    console.log("Signup: Internal Servor Error Debug Message");
    console.log(error);
    return {success : false, error : "Internal Server Error. Please try again later."};
  }
  return { success: true }
}

export async function logout() {
  try{
    const res = await fetch("http://localhost:8080/api/v1/auth/logout",{
        credentials : "include",
    });
    if(res.ok){
        console.log("Logout Successful");
    }
    else{
        console.log("Logout Failed");
    }
  }
  catch(error){
      console.log("Logout: Internal Server Error Occured");
  }
  redirect("/");
}

export async function getCurrentUser(){
  try{
    const res = await fetch("http://localhost:8080/api/v1/auth/status",{
        credentials: "include",
    })
    if(res.ok){
      return await res.json();
    }

    else{
      return null;
    }

  }
  catch(error){
      console.log("Authenticate User: Internal Server Error Occured.");
      console.log(error);
      return null;
  }
}