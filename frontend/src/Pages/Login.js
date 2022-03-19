import {useRef } from "react"; 

function Login() {
    const emailRef = useRef(); 
    const passwordRef = useRef();

    function onLogin() {
        const loginData = {
            email: emailRef.current.value,
            password: passwordRef.current.value,
        }
        fetch("https://mihkeljava.herokuapp.com/login",{
            method: "POST",
            body: JSON.stringify(loginData),
            headers: {
                "Content-Type":"application/json"
            }
        }).then(res => res.json()) 
        .then(data => {
            if (data.token) {
              sessionStorage.setItem("token", data.token);
            }  
        })
    }

    return ( 
    <div className="center">
        <label>E-mail</label> <br />
        <input type="text" ref={emailRef} /> <br />
        <label>Parool</label> <br />
        <input type="password" ref={passwordRef} /> <br />
        <button variant="dark" onClick={onLogin}>Logi sisse</button>
    </div>)
}

export default Login;