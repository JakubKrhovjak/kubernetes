import {useEffect, createContext, Fragment} from "react";
import {axios} from "./config/axiosCofnig";

// const SecurityContext =  createContext();

function Private() {

    // useEffect(() => {
    //     axios.get("simple-rest/test")
    //         .then(res => console.log(res));
    //
    // }, [])

    return (
        <Fragment>
            <h1>Private</h1>
        </Fragment>
    );

}

export default Private;
