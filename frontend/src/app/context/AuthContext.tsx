'use client';
import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext<AuthContextType | null>(null);

/** Note: We need to set different status codes based on type of exception.
 *  Ex: For a 403 or 401 Unauthorized when logging in -> Should set status code to 401.
 * 
 * @param param0 
 * @returns 
 */
export function AuthProvider({children }: {children : React.ReactNode }){
    const [user, setUser] = useState(null);
    const [loggedIn, setLoggedIn] = useState(false);
    const [loading, setLoading] = useState(true);

    const checkAuth = async () => { 
        try{
            const res = await fetch("http://localhost:8080/api/v1/auth/status",{
                credentials: "include",
            })
            
            if(res.ok){
                setLoggedIn(true);
                console.log("User logged in");
                const data = await res.json();
                console.log(data);
                setUser(data);
            }
            else{ //res.status == 403
                setLoggedIn(false);
                console.log("User isn't logged in...");
                setUser(null);
            }

        }
        catch(error){
            setLoggedIn(false);
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
        const responseBody = {
            "email" : email,
            "password" : password
        }

        try{
            const res = await fetch("http://localhost:8080/api/v1/auth/login",{
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                  },
                body : JSON.stringify(responseBody),
                credentials : "include"
            });
            if(res.ok){
                // setValidCredentials(true);
                // setErrorMessage("Incorrect Email and/or Password.");
                return true;
            }
            else{
                // setValidCredentials(false);
                // setErrorMessage("Incorrect Email and/or Password.");
                return false;
            }
        }
        catch(error){
            
            // setValidCredentials(false);
            // setErrorMessage("Internal Server Error Occured. Please try again later.");
        }

        await checkAuth();
    }

    const logout = async() => {
        try{
            const res = await fetch("http://localhost:8080/api/v1/auth/logout",{
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
        }
        finally{
            checkAuth();
        }
    }

    return (
        <AuthContext value={{ user, isAuthenticated: !user , loading, login, logout }}>
            {children}
        </AuthContext>
      );
};

// export const useAuth = () => useContext(AuthContext);

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) throw new Error("useAuth must be used within AuthProvider");
    return context;
  }