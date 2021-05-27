import {authModule} from './AuthModule.js';
class UserModule{
    async userList(){
        let response = await fetch('listUsersJson', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json;charset:utf-8' }
          });
          if (response.ok) {
             return await response.json();
          } else {
            document.getElementById('info').innerHTML = 'Ошибка сервера';
            return null;
          }
    }
    async rolesList(){
        let response = await fetch('rolesJson', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json;charset:utf-8' }
          });
          if (response.ok) {
             return await response.json();
          } else {
            document.getElementById('info').innerHTML = 'Ошибка сервера';
            return null;
          }
    }
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
    addMoneyForm(){
        document.getElementById('content').innerHTML = 
        `<form>
            <input placeholder="Money" id="money" name = "money" value=""><br>
            <input style="" id="addMoney" type="submit">
        </form>`;
        document.getElementById('addMoney').addEventListener('click', userModule.addMoney);
        
    }
    async addMoney(){
        const moneyV = document.getElementById("money").value;
        const money = {
            "money": moneyV
        };
        const response = await fetch('addMoney',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset:utf8'
            },
            body: JSON.stringify(money)
        });
    }
    async getUsers(){
        var result = "";
        let response = await fetch('users', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json;charset:utf-8' }
          });
          if (response.ok) {
            return await response.json();
          } else {
            document.getElementById('info').innerHTML = 'Ошибка сервера';
            return null;
          }
    }
    async users(){
        var users = await userModule.getUsers();
        for (let user of users){
            document.getElementById("content").innerHTML += `
                <li>${user.login} | ${user.money}</li>`;
        }
    }
    async histories(){
        var result = "";
        let response = await fetch('histories', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json;charset:utf-8' }
          });
          if (response.ok) {
            return await response.json();
          } else {
            document.getElementById('info').innerHTML = 'Ошибка сервера';
            return null;
          }
    }
    async changeUserForm(){
        var output = "";
        var result = await this.productList();
        var roles = await this.rolesList();
        output +=`
        <form>
            <select id="user" size = "10">`;
        for(let user of result){
            output +=`<option value="${user.id}">${user.login}|${user.password}</option>`;
        }
        output +=`
            </select>
            <input id="login" placeholder="login" name="login" value="">    <br>
            <select id="role" size = "3">`
            for(let role of roles){
                output +=`<option value="${user.id}">${user.login}|${user.password}</option>`;
            }
        output +=
           `</select>
            <input id="password" placeholder="password" name = "password" value=""><br>
            <input style="" id="submit" type="button" value="Enter">
        </form>`;
        document.getElementById("content").innerHTML = output;
        document.getElementById("submit").addEventListener("click",userModule.changeUser);
    }
    async changeUser(){
        const data = {
            "id" : document.getElementById("user").value,
            "login" : document.getElementById("login").value,
            "password" : document.getElementById("password").value
        };
        const response = await fetch('changeUserJson', {
         method: 'POST',
         body: JSON.stringify(data)
       });
       
       if(response.ok){
        const result = await response.json();
        document.getElementById('info').innerHTML = result.info;
        console.log("Request status: "+result.requestStatus);
        productModule.changeProductForm();
      }else{
        console.log("Ошибка получения данных");
      }
    }
}
const userModule = new UserModule();
export {userModule};


