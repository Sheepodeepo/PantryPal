import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({children }: {children : React.ReactNode }){
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    const checkAuth = async () => { 
        try{
            const res = await fetch("http://localhost:8080/api/v1/auth/status",{
                credentials: "include",
            })
            const data = await res.json();
            setUser(data);
        }
        catch(error){
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
                // return true;
            }
            else{
                // setValidCredentials(false);
                // setErrorMessage("Incorrect Email and/or Password.");
                // return false;
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
    }

    return (
        <AuthContext value={{ user, isAuthenticated: !user, loading, login, logout }}>
        {children}
      </AuthContext>
        // <AuthContext.Provider value={{ user, isAuthenticated: !user, loading, login, logout }}>
        //   {children}
        // </AuthContext.Provider>
      );
};

export const useAuth = () => useContext(AuthContext);

// export function useAuth() {
//     const context = useContext(AuthContext);
//     if (!context) throw new Error("useAuth must be used within AuthProvider");
//     return context;
//   }