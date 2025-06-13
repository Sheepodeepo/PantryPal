import { useAuth } from "../context/AuthContext";

export default function LogoutBtn(){
    const { logout } = useAuth();
    const handeLogout = async() => {
        try{
            const res = await fetch("http://localhost:8080/api/v1/auth/logout",{

            });
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
        <>
            <button
                onClick={logout}
                className="px-4 cursor-pointer" 
                // className=" px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
                >
                Logout
            </button>
        </>
    )
}