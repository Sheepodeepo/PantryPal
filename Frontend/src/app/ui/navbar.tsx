import Link from "next/link";


export default function Navbar(){
    return (
        <div className="w-full">
            <div className="flex items-center px-8 md:px-32 min-h-16 bg-sky-500 text-lg">
                <Link
                    className="flex grow"
                    href={"/"}> 
                    PantryPal 
                </Link>

                <div className="flex grow justify-end">       
                    <Link 
                        className=""
                        href={"/login"}>
                        Login
                    </Link>
                </div>

            </div>

            {/* Mobile Menu/ Hamburger menu */}
        </div>

    )
}