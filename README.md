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

_Adapted and Built upon [UCSD CSE 110 Github Project](https://github.com/ucsd-cse110-fa23/cse-110-project-team-32)_ 