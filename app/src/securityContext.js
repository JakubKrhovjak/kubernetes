import React, {useContext, useEffect} from "react";
import {axios} from "./config/axiosCofnig";

const UserProvider = () => {

    const [jwt, setJwt] = useContext("")

    useEffect(() => {
        axios.post("simple-rest/login", {username: "first-user", password: "tajne"})
            .then(res => setJwt(res));

    }, [])

    return(
        <UserProvider.Provider
        <div>

        </div>
    )

}