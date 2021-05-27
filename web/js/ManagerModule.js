/* 
 * <a class="btn btn-primary" id="addProduct" role="button">Add product</a>
        <a class="btn btn-primary" id="changeProduct" role="button">Change product</a>
        <a class="btn btn-primary" id="users" role="button">users</a>
        <a class="btn btn-primary" id="histories" role="button">histories</a>
        <a class="btn btn-primary" id="addTag" role="button">Add tag</a>
        <a class="btn btn-primary" id="changeProductTags" role="button">Change product tag</a>
        <a class="btn btn-primary" id="changeTag" role="button">Change tag</a>
 */
import {productModule} from "./ProductModule.js";
import {userModule} from "./UserModule.js";
class ManagerModule{
    printAdminMenu(){
        document.getElementById("content").innerHTML = `
            <a class="btn btn-primary" id="addProduct"        role="button">Add product</a>
            <a class="btn btn-primary" id="changeProduct"     role="button">Change product</a>
            <a class="btn btn-primary" id="users"             role="button">users</a>
            <a class="btn btn-primary" id="histories"         role="button">histories</a>
            <a class="btn btn-primary" id="addTag"            role="button">Add tag</a>
            <a class="btn btn-primary" id="changeProductTags" role="button">Change product tag</a>
            <a class="btn btn-primary" id="changeTag"         role="button">Change tag</a>`;
        document.getElementById("addProduct").addEventListener('click', function(){
            productModule.printAddProductForm();
        });
        document.getElementById("changeProduct").addEventListener('click', function(){
            productModule.changeProductForm();
        });
        document.getElementById("users").addEventListener('click', function(){
            userModule.users();
        });
        document.getElementById("histories").addEventListener('click',  function(){
            managerModule.histories();
        });
        document.getElementById("addTag").addEventListener('click',  function(){
            managerModule.addTagForm();
        });
        document.getElementById("changeProductTags").addEventListener('click',  function(){
        });
        document.getElementById("changeTag").addEventListener('click',  function(){
        });
    }
    async histories(){
        let response = await fetch('histories', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json;charset:utf-8' }
          });
          if (response.ok) {
             var result = await response.json();
          } else {
            document.getElementById('info').innerHTML = 'Ошибка сервера';
            return null;
          }
        document.getElementById("content").innerHTML = "";
        for (let history of result){
            document.getElementById("content").innerHTML += `<li>${history.login} | ${history.name} | ${history.price} | ${history.count} |${history.takeOn}</li>`;
        }
    }
    async addTagForm(){
        let response = await fetch('tagsJson', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json;charset:utf-8' }
          });
          if (response.ok) {
             var result = await response.json();
          } else {
            document.getElementById('info').innerHTML = 'Ошибка сервера';
            return null;
          }
          for(let tag of result){
              document.getElementById('info').innerHTML +=`<div class="col-sm-9"> <li>${tag}</li></div>`;
          }
          document.getElementById("content").innerHTML += `<div class="col-sm-9"> 
                <input type="text" class="form-control" id="name" name="name" value="">
                <input type="button" id = "submit" value="submit">
            </div>`;
        document.getElementById("submit").addEventListener("click", managerModule.addTag);
    }
    async addTag(){
        const val = {"name": document.getElementById("name").value};
        const response = await fetch('addTagJson', {
         method: 'POST',
         body:  JSON.stringify(val)
       });
       if(response.ok){
        const result = await response.json();
        document.getElementById('info').innerHTML = result.info;
        console.log("Request status: "+result.requestStatus);
        document.getElementById('content').innerHTML='';
      }else{
        console.log("Ошибка получения данных");
      }
        
    }
    
        
};
  


let managerModule = new ManagerModule();
export {managerModule};




