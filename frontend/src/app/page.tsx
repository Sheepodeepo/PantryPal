'use client';
import { useAuth } from "@/lib/actions/AuthContext";
import Link from "next/link";
import { useEffect, useState, useCallback  } from "react";
import { HiArrowRight } from "react-icons/hi2";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  // DropdownMenuLabel,
  // DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/ui/dropdown-menu"
import { Recipe } from "@/lib/types";

export default function Home(){
  const { isAuthenticated, loading } = useAuth();
  const [ recipes, setRecipes] = useState([]);
  const apiBaseUrl = process.env.NEXT_PUBLIC_API_URL;

  const fetchRecipes = async () => {
    try{
      const res = await fetch(`${apiBaseUrl}/api/v1/recipe`,{
        credentials : "include",
      })
      const recipeData = await res.json();
      setRecipes(recipeData);
    }
    catch(error){
      console.log(error);
    }
  }


  useEffect(() => {
    if(isAuthenticated){
      fetchRecipes();
    }
  }, [fetchRecipes,isAuthenticated]);

  const handleDeleteRecipe = async(recipeId : number) => {    
    try {
      await fetch(`${apiBaseUrl}/api/v1/recipe/${recipeId}`,{
        method : "DELETE",
        credentials : "include",
      })
      fetchRecipes();
    } catch (error) {
        console.log("Internal Server Error Occured.");
        console.log(error);
    }
  }

  // 👇 Add this early return
  //Condition should be loading -> Create a skeleton UI (NextJs thingy with fancy tailwind css to mock loading objects)
  //Skeleton UI
  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <p className="text-lg">Loading...</p>
      </div>
    );
  }

  return ( 
    <>
      {isAuthenticated ? (
          //User Home Page
          <>
            <div className="flex flex-col gap-y-4 py-4 max-w-4xl mx-auto min-h-16 ">
                {recipes.map((recipe : Recipe) => (
                  
                  <div key={recipe.id} className="flex flex-col mx-6 md:mx-0 gap-y-1 p-4 border rounded-lg bg-amber-400 shadow">
                    <div className="flex justify-between">
                      <Link
                        href={`/recipe/${recipe.id}`}
                      >
                        <h2 className="text-lg md:text-2xl font-bold">{recipe.name}</h2>
                      </Link>

                      <DropdownMenu>
                        <DropdownMenuTrigger>
                          <span className="px-4 text-xl">...</span>

                        </DropdownMenuTrigger>
      
                        <DropdownMenuContent>
                          <DropdownMenuItem>
                            <button onClick={() => {handleDeleteRecipe(recipe.id)}}>
                              Delete  
                            </button> 
                          </DropdownMenuItem>
                        </DropdownMenuContent>
                     </DropdownMenu>
                    </div>

                    <p className="text-md italic"> Ingredients</p>
                    <p className="text-sm">{recipe.ingredients} </p>

                    <p className="text-md  italic"> Instructions </p>
                    <p className="text-sm">{recipe.instructions}</p>
                    <p className="text-gray-700 text-sm italic">{new Date(recipe.updatedDate || recipe.createdDate).toLocaleDateString()}</p>
                  </div>
    
                ))}
            </div> 
          </>
          )
          :
       // Public Landing Page
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


      }
    </>
  );
}