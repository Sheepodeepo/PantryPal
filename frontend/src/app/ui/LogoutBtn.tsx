import { useAuth } from "../context/AuthContext";

export default function LogoutBtn(){
    const { logout } = useAuth();

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