'use client';

import Navbar from "@/app/ui/navbar";
import Link from "next/link";
import Button from "../ui/button";
import { FormEvent, useState } from "react";

export default function LoginForm(){
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [isError, setIsError] = useState(false);
    
    const validateEmail = () =>{
        const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if(re.test(email)){
            setIsError(false);
            setErrorMsg('');
            return true;
        }
        else{
            setIsError(true);
            setErrorMsg("Invalid Email.");
            return false;
        }
    }

    const validatePassword = () => {
        const re = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/; //Checks length 8+, symbol, upper+lwrcase letters, & a number.
        if(re.test(password)){
            setIsError(false);
            setErrorMsg('');
            return true;
        }
        else{
            setIsError(true);
            setErrorMsg("Password isn't at least 8 characters long or doesn't contain an" +
                " uppercase, lowercase letter, unique symbol, or number.");
            return false;
        }
    }

    const handleSubmit = (e: FormEvent<HTMLFormElement>) =>{
        e.preventDefault();
        validateEmail();
        validatePassword();
    }
    return (
        <div className="">
            <Navbar/>
 
            <div className="min-h-screen flex items-center justify-center p-4">
                <div className="w-full max-w-md bg-white rounded-xl border-black border-2 shadow-2xl p-6 space-y-6">
                    <div className="text-2xl font-bold text-center text-gray-800">Login to Pantry Pal</div>
                    <form 
                    onSubmit={handleSubmit}
                    className="space-y-4">
                        <div>
                            <label className="block text-sm font-medium text-gray-700">Email</label>
                            <input
                            type="email"
                            placeholder="you@example.com"
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700">Password</label>
                            <input
                            type="password"
                            placeholder="•••••••••"
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            />
                        </div>
                        <button
                            type="submit"
                            className="w-full px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 cursor-pointer"
                        >
                            Sign In
                        </button>
                    </form>

                    <div className="flex flex-col gap-4">
                        <Link
                        href={"/register"}
                        className="text-sm text-center text-gray-500 "
                        >
                            <p className="text-blue-500 hover:underline ">Forgot your password?</p>
                                            
                        </Link>
                        <hr className="flex items-center text-sm"/>

                        <Link
                        href={"/register"}
                        className="text-sm text-center text-gray-500"
                        >
                            <p className="text-blue-500 hover:underline ">Don't have an account? Sign Up</p>
                        
                        </Link>
                    </div>

                </div>
            </div>
        </div>

    )
}