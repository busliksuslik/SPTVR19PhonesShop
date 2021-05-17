class AuthModule{
    printLoginForm(){
        document.getElementById("content").innerHTML = `
            <input placeholder="name" name="login" id="login" value=""><br>
            <input type="password" placeholder="Password" id="password" name = "password" value=""><br>
            <input id="btnEnter" type="button" value="Enter"><br>
            <a id="registration-link" href="#registration">registration</a>`;
        document.getElementById("btnEnter").addEventListener('click', authModule.auth);
        document.getElementById("registration-link").addEventListener('click', authModule.registration);
    }
    //document.getElementById("btnEnter").addEventListener('click',userModule.printRegistrationForm);
    auth(){
        //console.log("aahahahahahahahahahahahaahh");
        document.getElementById("content").innerHTML = ``;
    }
    registration(){
        console.log("aahahahahahdfsdfsdfsdfsdfggfhhh");
        document.getElementById("content").innerHTML = ``;
    }
}
let authModule = new AuthModule();
export {authModule};


