'use client';

import { useState, useEffect } from "react"
import PublicHomePage from "./ui/pages/PublicHomePage"
import UserHomePage from "./ui/pages/UserHomePage";
import Navbar from "./ui/navbar";
import { AuthProvider } from "./context/AuthContext";

export default function Home(){
  const [loginStatus, setLoginStatus] = useState(false);


  useEffect(() => {
    const checkLoginStatus = async () =>{
      try{
        const res = await fetch("http://localhost:8080/api/v1/auth/status",{
          credentials : "include"
        })
        const data = await res.json();
        console.log(data);
        // if(res.ok){
        //   setLoginStatus(true);
        //   console.log("User Logged In");
        // }
        // else{
        //   setLoginStatus(false);
        //   console.log("User logged out");
        // }
      } 
      catch(error){
        setLoginStatus(false);
        console.log("Internal Server Error Occured");
        console.log(error);
      }
    };
    checkLoginStatus();
  },[]);



  return ( 
    <AuthProvider>
      <div className="">
        <Navbar/> 
        {loginStatus ? <UserHomePage/> : <PublicHomePage/>}
      </div>
    </AuthProvider>

  )
}