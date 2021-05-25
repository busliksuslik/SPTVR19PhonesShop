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
        });
        document.getElementById("addTag").addEventListener('click',  function(){
        });
        document.getElementById("changeProductTags").addEventListener('click',  function(){
        });
        document.getElementById("changeTag").addEventListener('click',  function(){
        });
    }
    
        
};
  


let managerModule = new ManagerModule();
export {managerModule};




