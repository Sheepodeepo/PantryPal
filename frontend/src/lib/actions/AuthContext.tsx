'use client';
import { createContext, useContext, useState, useEffect } from "react";
import { User, AuthContextType } from "@/lib/types";

const AuthContext = createContext<AuthContextType | null>(null);
const apiBaseUrl = process.env.NEXT_PUBLIC_API_URL;

export function AuthProvider({children }: {children : React.ReactNode }){
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);

    const checkAuth = async () => { 
        try{
            const res = await fetch(`${apiBaseUrl}/api/v1/auth/status`,{
                credentials: "include",
            })
            
            if(res.ok){
                const data = await res.json();
                setUser(data);
            }
            else{ //res.status == 403
                setUser(null);
            }

        }
        catch(error){
            // setLoggedIn(false);
            setUser(null);
            console.log("Internal Server Error Occured.");
            console.log(error);
        }
        finally{
            setLoading(false);
        }
    };

    useEffect(() => {
        checkAuth();
    },[]);

    const login = async (email : string, password: string) => {
        const responseBody = { email, password };

        try{
            const res = await fetch(`${apiBaseUrl}/api/v1/auth/login`,{
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                  },
                body : JSON.stringify(responseBody),
                credentials : "include"
            });
            if(res.ok){
                console.log("Login Successful");
                
                await checkAuth();
                return true;
            }
            else{
                setUser(null);
                console.log("Login Failed");
                return false;
            }
        }
        catch(error){
            setUser(null);
            console.log("Internal Server Error Occured");
            console.log(error);
            return false;
        }
    }

    const signup = async(email : string, password: string) =>{
        try{
            const res = await fetch(`${apiBaseUrl}/api/v1/auth/signup`,{
                method : "POST",
                headers: {
                    'Content-Type': 'application/json',
                  },
                body : JSON.stringify({email, password}),

            });
            if(res.ok){
                return true;
            }
            else if(res.status==409){
                return false;
            }
            return false;
        }
        catch(error){
            console.log("Internal Server Error Occured");
            console.log(error);
            return false;
        }
    }

    const logout = async() => {
        try{
            const res = await fetch(`${apiBaseUrl}/api/v1/auth/logout`,{
                credentials : "include",
            });
            setUser(null);
            if(res.ok){
                console.log("Logout Successful");
            }
            else{
                console.log("Logout Failed");
            }
        }
        catch(error){
            console.log("Internal Server Error Occured");
            console.log(error);
        }
        finally{
            checkAuth();
        }
    }

    return (
        <AuthContext value={{ user, isAuthenticated: (user != null) , loading, login, signup, logout }}>
            {children}
        </AuthContext>
      );
};

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) throw new Error("useAuth must be used within AuthProvider");
    return context;
  }