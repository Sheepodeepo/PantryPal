import Link from "next/link";
import LogoutBtn from "./LogoutBtn";


export default function Navbar(){
    return (
        <div className="w-full bg-sky-500 text-xl">
            {/* px-8 md:px-80 */}
            <div className="flex items-center max-w-4xl mx-auto min-h-16">
                <Link
                    className="flex grow justify-start px-4"
                    href={"/"}> 
                    PantryPal 
                </Link>

                <div className="flex grow justify-end px-4">       
                    <Link 
                        className="px-4"
                        href={"/register"}>
                        Register 
                    </Link>
                    <Link 
                        className="px-4"
                        href={"/login"}>
                        Login
                    </Link>

                    <LogoutBtn/>
                </div>

            </div>

            {/* Mobile Menu/ Hamburger menu */}
        </div>

    )
}