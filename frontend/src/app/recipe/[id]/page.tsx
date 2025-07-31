import { notFound } from "next/navigation";
import { cookies } from "next/headers";
import Link from "next/link";
import { Recipe } from "@/lib/types";

export const dynamic = "force-dynamic"; // prevent static optimization
export default async function RecipePage({ params }: { params: Promise<{ id: string }> }) {
    const { id } = await params;
    let recipe: Recipe | null = null;
    const cookieStore = await cookies()
    const jwt = cookieStore.get('JWT')?.value;
    const apiBaseUrl = process.env.NEXT_PUBLIC_API_URL;
    if (!jwt) {
        console.error("JWT token is missing");
        notFound();
    }
    if (!apiBaseUrl) {
        console.error("API base URL is not defined");
        notFound();
    }
    if (!id) {
        console.error("Recipe ID is missing");
        notFound();
    }

    // Fetch the recipe data from the API
    try{
        const res = await fetch(`${apiBaseUrl}/api/v1/recipe/${id}`, {
            headers : {
                "Content-Type": "application/json",
                "Cookie" : `JWT=${jwt}`, // Include JWT in the request headers
                'Authorization': jwt ? `Bearer ${jwt}` : '',
            },
            cache: "no-store", // Ensure fresh data
        });
        if (res.status === 404) {
            notFound();
        }
        if (!res.ok) {
            console.error("Failed to fetch recipe:", res.statusText);
            throw new Error(`Failed to fetch recipe: ${res.status}`);
        }
        recipe = await res.json();
    }
    catch(error){
        console.log("Internal Server Error", error);
        throw new Error(`Failed to fetch recipe: ${error}`);
    }

    if (recipe == null) return null; // safety fallback
    
      return (
        <>
            <div className="max-w-4xl mx-auto my-6 py-4 px-8 bg-amber-400 md:rounded-xl">
                <div className="flex flex-col gap-y-3">
                    <h1 className="text-3xl font-bold">{recipe.name}</h1>
                    <p className="text-gray-600">
                        {new Date(recipe.updatedDate || recipe.createdDate).toLocaleDateString()}
                    </p>
                
                    <div className="flex flex-col gap-y-2">
                        <h2 className="text-xl font-semibold">Ingredients</h2>
                        <p>{recipe.ingredients}</p>
                    </div>
                
                    <div className="flex flex-col gap-y-2">
                        <h2 className="text-xl font-semibold">Instructions</h2>
                        <p>{recipe.instructions}</p>
                    </div>

                    <Link 
                    href = "/"
                    className= "w-fit bg-black rounded-lg text-white shadow-xl"
                    >
                    <p className="p-2"> Return </p> 
                </Link>    
                </div>
            </div>    
        </>

      );
}