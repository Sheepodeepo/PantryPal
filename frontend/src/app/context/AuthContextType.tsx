interface AuthContextType{
    user : any,
    isAuthenticated: boolean,
    // loading : boolean,
    login: (email: string, password: string) => Promise<boolean | void>;
    logout: () => Promise<void>;
}