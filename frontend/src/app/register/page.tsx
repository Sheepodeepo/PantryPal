'use client';

import Navbar from "../ui/navbar"
import { useState } from "react";

export default function Register(){
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');
        const [errorMsg, setErrorMsg] = useState('');
        const [isError, setIsError] = useState(false);

    // const validateEmail = () =>{
    //     const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    //     if(re.test(email)){
    //         setIsError(false);
    //         setErrorMsg('');
    //         return true;
    //     }
    //     else{
    //         setIsError(true);
    //         setErrorMsg("Invalid Email and/or Password.");
    //         return false;
    //     }
    // }
    
    // const validatePassword = () => {
    //     const re = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/; //Checks length 8+, symbol, upper+lwrcase letters, & a number.
    //     if(isError){
    //         setIsError(true);
    //         return false;
    //     }
    
    //     if(re.test(password)){
    //         setIsError(false);
    //         setErrorMsg('');
    //         return true;
    //     }
    //     else{
    //         setIsError(true);
    //         setErrorMsg("Password isn't at least 8 characters long or doesn't contain an" +
    //             " uppercase, lowercase letter, unique symbol, or number.");
    //         return false;
    //     }
    // }
    
    return (
        <div className="">
            <Navbar/>
            <div className="">
                Register Page
            </div>
        </div>
    )
}

