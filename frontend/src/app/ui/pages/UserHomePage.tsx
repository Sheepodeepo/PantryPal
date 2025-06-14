import Navbar from "../navbar";

export default function UserHomePage({recipes} : any){
    return (
        <>
        <div className="flex flex-col gap-y-4 py-4 max-w-4xl mx-auto min-h-16 ">
            {recipes.map((recipe : any) => (
              <div key={recipe.id} className="p-4 border rounded-lg bg-amber-400 shadow">
                <h2 className="text-2xl font-bold">{recipe.name}</h2>
                <p>{recipe.ingredients} </p>
                <p>{recipe.instructions}</p>
                <p>{!recipe.updatedDate ? recipe.createdDate :  recipe.updatedDate}</p>
              </div>

            ))}
        </div> 
        </>

    )
}