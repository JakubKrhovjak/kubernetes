import {useEffect} from "react";
import axios from "axios";

function App() {

    useEffect(() => {
        axios.get("http://localhost:8080/simple-rest/test")
            .then(res => console.log(res));

    })

    return (
        <div className="App">
            <h1>React</h1>
        </div>
    );

}

export default App;
