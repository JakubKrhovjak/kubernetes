import {useEffect, createContext, Fragment} from "react";
import {axios} from "./config/axiosCofnig";

// const SecurityContext =  createContext();

function App() {

    useEffect(() => {
        axios.get("simple-rest/test")
            .then(res => console.log(res));

    }, [])

    return (
        <Fragment>
            <Login></Login>
        </Fragment>
    );

}

export default App;
