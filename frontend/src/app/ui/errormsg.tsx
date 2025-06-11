export default function ErrorMsg({message}: ErrorFormProps){
    //{ title, count }: MyComponentProps
    return (
        <div className="">
            <div className="text-red-500 font-medium">
                {message}
            </div>
        </div>
    )
}