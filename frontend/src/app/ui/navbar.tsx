"use client";

import Link from "next/link";
import LogoutBtn from "./LogoutBtn";
import { useAuth } from "../context/AuthContext";


export default function Navbar(){
    const { isAuthenticated } = useAuth();

    return (
        <div className="w-full bg-sky-500 text-xl">
            <div className="flex items-center max-w-4xl mx-auto min-h-16">
                <Link
                    className="flex grow justify-start px-4"
                    href={"/"}> 
                    PantryPal 
                </Link>
                { isAuthenticated ? 
                    <>
                        <LogoutBtn/>
                    </>       
                    :
                    <>
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
                        </div>
                    </>
                }

            </div>

            {/* Mobile Menu/ Hamburger menu */}
        </div>

    )
}