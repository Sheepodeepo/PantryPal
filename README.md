# PantryPal

Web Application for users to generate recipes when they don't know what to cook and wants some inspiration based on ingredients in their pantry. 

**[Website Link](pantry-pal-liard-iota.vercel.app)**

---

## Tech Stack

#### Frontend
- Next.Js/React
- Tailwind Css 

#### Backend
- Springboot + Gradle
- PostgreSQL

#### DevOps/Cloud Tools
- Vercel(Deploy frontend with Vercel)
- Supabase(Store PostgreSQL Table swith Supabase)
- Github Actions CI/CD (To Verify Builds)
- AWS EC2 - * To Deploy Backend Server *
- Docker - To build Backend Application to run on EC2 Instance 

## Features 
- Account Signup/Signin with Spring Security + JWT
- Storage of Recipes with Spring Data JDBC