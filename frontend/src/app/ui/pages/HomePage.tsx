import Navbar from "@/app/ui/navbar";
import Link from "next/link";
import { HiArrowRight } from 'react-icons/hi2';
import { useAuth } from "@/app/context/AuthContext";
import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import UserHomePage from "./UserHomePage";

export default function HomePage(){
  const { isAuthenticated } = useAuth();
  const [ recipes, setRecipes] = useState([]);
  console.log(`Recipe Lst: ${recipes}`);

  useEffect(() => {
    
    const fetchRecipes = async () => {
      try{
        const res = await fetch("http://localhost:8080/api/v1/recipe",{
          credentials : "include",
        })
        const recipeData = await res.json();
        console.log(recipeData);
        setRecipes(recipeData);

      }
      catch(error){
        console.log(error);
      }
    }
    fetchRecipes();
  }, []);

  return ( 
    <>
      {isAuthenticated ? 
      // Public Home Page
        <div className="relative bg-[url('/images/1920.jpg')] bg-cover flex flex-col items-center justify-center w-auto h-screen">
          <div className="flex flex-col items-center text-white ">
            <div className="text-2xl md:text-5xl font-bold py-2 ">Welcome to Pantry Pal. </div>
            <div className="text-base md:text-3xl text-center font-bold max-w-2xl py-2 "> Where you can discover all sorts of different recipes. </div>
            <Link 
              href ="/login"
              className="flex flex-row items-center py-2 bg-black text-white px-2 rounded-lg">
                <span className="text-base md:text-xl px-2"> Log in </span> <HiArrowRight className="w-5 md:w-6"/>
            </Link>
          </div>
        </div>
        :
      //User Home Page
      <>
        <UserHomePage recipes={recipes}/>
      </>


      }
    </>


  )
}