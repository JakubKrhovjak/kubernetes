import {useEffect, createContext, Fragment} from "react";
import {axios} from "./config/axiosCofnig";

// const SecurityContext =  createContext();

function Login() {

    useEffect(() => {
        axios.get("/simple-rest/auth/login")
            .then(res => console.log(res));

    }, [])

    return (
        <Fragment>
            H
        </Fragment>
    );

}

export default Login;
