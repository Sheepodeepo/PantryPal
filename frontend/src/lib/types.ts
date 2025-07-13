interface Recipe {
    id: number;
    name: string;
    ingredients: string;
    instructions: string;
    createdDate: string;
    updatedDate?: string;
}

interface User{
    email: string;
}

interface AuthContextType{
    user: User | null;
    isAuthenticated: boolean,
    loading : boolean,
    login: (email: string, password: string) => Promise<boolean | void>;
    signup: (email: string, password: string) => Promise<boolean | void>;
    logout: () => Promise<void>;
}

interface ErrorFormProps{
    message: string
}

export type {AuthContextType, ErrorFormProps, Recipe, User}