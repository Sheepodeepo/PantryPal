'use client';

import { useState, useEffect } from "react"
import HomePage from "./ui/pages/HomePage";
import Navbar from "./ui/navbar";

export default function Home(){

  return ( 
      <div className="">
        <Navbar/> 
        <HomePage/>
      </div>
  )
}