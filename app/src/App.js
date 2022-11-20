import {useEffect} from "react";
import { axios } from "./config/axiosCofnig";

function App() {

    useEffect(() => {
        axios.get("simple-rest/test")
            .then(res => console.log(res));

    })

    return (
        <div className="App">
            <h1>React</h1>
        </div>
    );

}

export default App;
