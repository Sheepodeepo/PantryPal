"use client";

import Link from "next/link";
import { useAuth } from "@/lib/actions/AuthContext";
import { Button} from "@/ui/button";
import { ThemeToggle } from "@/ui/theme-toggle";
import { useState } from "react";
import Hamburger from "hamburger-react";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
  } from "@/ui/dropdown-menu"

export default function Navbar(){
    const { isAuthenticated, logout } = useAuth();
    const [isOpen, setOpen] = useState(false)
    const [isMenu, setMenu] = useState(false);

    return (
        <div className="w-full bg-sky-500 text-xl">
            <div className="flex items-center max-w-4xl mx-auto min-h-16">
                {/* <div className=""> */}
                    <Link
                        className="flex grow justify-start px-4"
                        href={"/"}> 
                        <p className="text-2xl font-semibold"> PantryPal  </p>
                    </Link>
                {/* </div> */}
                <div className="hidden md:block px-6">
                    { isAuthenticated ? 
                        <div className="flex gap-x-4">
                            <Button size={"lg"} className="bg-black" asChild>
                                <Link href="/create"> Add Recipe </Link>
                            </Button>

                            <Button size={"lg"} className="" onClick={logout} >
                                Logout
                            </Button>
                            {/* <button
                                onClick={logout}
                                className="flex flex-row items-center py-2 bg-black text-white rounded-lg px-4 cursor-pointer text-base" 
                                >
                                Logout
                            </button> */}
                        </div>                    :
                        <div className="flex grow justify-end gap-x-4">    
                            {/* <ThemeToggle/> */}
                            <Button size={"lg"} className="bg-black" asChild>
                                <Link href="/login" className="text-white"> Login </Link>
                            </Button>
                            <Button size={"lg"} className="bg-black" asChild>
                                <Link href="/signup" className="text-white"> Sign Up </Link>
                            </Button>
                        </div>
                    }
                </div>

                <div className="md:hidden px-6">
                    <DropdownMenu>
                        <DropdownMenuTrigger>
                            <Hamburger/>
                        </DropdownMenuTrigger>
      
                        <DropdownMenuContent>
                            {
                                isAuthenticated ? 
                                <>
                                    <DropdownMenuItem>
                                        <Link href="/create"> Add Recipe </Link>
                                    </DropdownMenuItem>
                                    <DropdownMenuItem>
                                        <button onClick={logout}>
                                            Logout
                                        </button>
                                    </DropdownMenuItem>
                                </>
                                :
                                <>
                                    <DropdownMenuItem>
                                        <Link href="/login" className="text-white"> Login </Link>
                                    </DropdownMenuItem>
                                    <DropdownMenuItem>
                                        <Link href="/signup" className="text-white"> Sign Up </Link>
                                    </DropdownMenuItem>                                
                                </>   
                            }
                        </DropdownMenuContent>
                    </DropdownMenu>
                </div>

            </div>
        </div>
    )
}
                            {/* <DropdownMenuSeparator />
                            <DropdownMenuItem>
                                <ThemeToggle></ThemeToggle>
                            </DropdownMenuItem> */}