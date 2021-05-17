import {authModule} from './AuthModule';
class UserModule{
    registration(){
        document.getElementById('context').innerHTML = `
        <input placeholder="Name" name="name" id="login" value="${name}"><br>
            <input placeholder="Password" name = "password" id="password" value="${password}"><br>
            <input id="registration" style="" type="button">`
        document.getElementById('registration').addEventListener('click', userModule.createUser);
    }
    createUser(){
        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;
    }
}
const userModule = new UserModule();
export {userModule};


