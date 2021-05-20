import {authModule} from './AuthModule.js';
class UserModule{
    registration(){
        document.getElementById('content').innerHTML = `
        <input placeholder="Name" name="name" id="login" value=""><br>
            <input placeholder="Password" name = "password" id="password" value=""><br>
            <input id="registration" style="" type="button" value="registration">`;
        document.getElementById('registration').addEventListener('click', userModule.createUser);
    }
    async createUser(){
        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;
        document.getElementById('info').innerHTML='';
        const user = {
            "login": login,
            "password": password
        };

       const response = await fetch('createUserJson',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset:utf8'
            },
            body: JSON.stringify(user)
        });
        if(response.ok){
          const result = await response.json();
          document.getElementById('info').innerHTML=result.info;
          console.log("Request status: "+result.requestStatus);
          authModule.printLoginForm();
        }else{
          console.log("Ошибка получения данных");
        }
    }
    async mutationOne(){
        var result = "";
        const response = await fetch('mutateOne',{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset:utf8'
            }
        });
        
        if(response.ok){
            result = await response.json();
            console.log(result);
            //var obj = JSON.parse(result);
            //console.log(obj);
        }
        alert(result);
        document.getElementById('content').innerHTML = `
        <h1>account mutation</h1>
        <input placeholder="Name" name="name" id="login" value="`+result.login+`"><br>
            <input placeholder="Password" name = "password" id="password" value=""><br>
            <input id="registration" style="" type="button" value="registration">`;
        document.getElementById('mutate').addEventListener('click', userModule.mutate);
    }
    async mutate(){
        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;
        document.getElementById('info').innerHTML='';
        const user = {
            "login": login,
            "password": password
        };
        const response = await fetch('mutateUserJson',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset:utf8'
            },
            body: JSON.stringify(user)
        });
    }
}
const userModule = new UserModule();
export {userModule};


