class AuthModule{
    printLoginForm(){
        document.getElementById("content").innerHTML = `
            <input type="text" placeholder="name" id="login" name="login" value=""><br>
            <input type="password" placeholder="Password" id="password" name = "password" value=""><br>
            <input id="btnEnter" type="button" value="Enter"><br>
            <a id="registration-link" href="#registration">registration</a>`;
            /*document.addEventListener('keydown', e =>{
                if (!e.repeat && e.key === "Enter"){
                    if (document.getElementById("password") === document.activeElement){
                    authModule.auth();
                    } else if (document.getElementById("login") === document.activeElement){
                        document.getElementById("password").focus();
                    }
                }
                return;
            });*/
        document.getElementById("btnEnter").addEventListener('click', authModule.auth);
        document.getElementById("registration-link").addEventListener('click', authModule.registration);
    }
    //document.getElementById("btnEnter").addEventListener('click',userModule.printRegistrationForm);
    async auth(){
        //console.log("aahahahahahahahahahahahaahh");
        //document.getElementById("content").innerHTML = ``;
        const login = document.getElementById("login").value;
        const password = document.getElementById("password").value;
        const credential= {
            "login": login,
            "password": password
        };
        const response = await fetch('loginJson', {
         method: 'POST',
         headers: {
          'Content-Type': 'application/json;charset:utf8'
         },
         body: JSON.stringify(credential)
       });
       
       if(response.ok){
        const result = await response.json();
        document.getElementById('info').innerHTML = result.info;
        console.log("Request status: "+result.requestStatus);
        document.getElementById('content').innerHTML='';
        if(result.requestStatus){
          sessionStorage.setItem('token',JSON.stringify(result.token));
          sessionStorage.setItem('role',JSON.stringify(result.role));
        }else{
          if(sessionStorage.getItem(token) !== null){
            sessionStorage.removeItem('token');
            sessionStorage.removeItem('role');
          }
        }
      }else{
        console.log("Ошибка получения данных");
      }
      authModule.toogleMenu();
      // console.log('Auth: token'+sessionStorage.getItem('token'));
      // console.log('Auth: role'+sessionStorage.getItem('role'));
    }
    async logout(){
      const response = await fetch('logoutJson',{
        method: 'GET',
        headers:{
          'Content-Type': 'application/json;charset:utf8'
        }
      });
      if(response.ok){
        const result = await response.json();
        if(result.requestStatus){
          sessionStorage.removeItem('token');
          sessionStorage.removeItem('role');
        }
        document.getElementById('info').innerHTML = "succ";
      }
      authModule.toogleMenu();
    }
    toogleMenu(){
      let role = null;
      if(sessionStorage.getItem('role') !== null){
        role = JSON.parse(sessionStorage.getItem('role'));
      }
      console.log('Auth: token - '+sessionStorage.getItem('token'));
      console.log('Auth: role - '+sessionStorage.getItem('role'));
      
      if(role===null){
        document.getElementById("products").style.display = 'block';
        document.getElementById("addUserForm").style.display = 'block';

      }else if(role==="CUSTOMER"){
        document.getElementById("products").style.display = 'block';
        document.getElementById("addUserForm").style.display = 'none';

      }else if(role==="MANAGER"){
        document.getElementById("products").style.display = 'block';
        document.getElementById("addUserForm").style.display = 'none';

      }else if(role==="ADMIN"){
        document.getElementById("products").style.display = 'block';
        document.getElementById("addUserForm").style.display = 'none';

      }
    
    }
}
let authModule = new AuthModule();
export {authModule};


