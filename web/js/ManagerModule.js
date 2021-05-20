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
        document.getElementById("addProduct").onclick = function (){
            productModule.printAddProductForm();
        };
        document.getElementById("changeProduct").onclick = function (){};
        document.getElementById("users").onclick = function (){};
        document.getElementById("histories").onclick = function (){};
        document.getElementById("addTag").onclick = function (){};
        document.getElementById("changeProductTags").onclick = function (){};
        document.getElementById("changeTag").onclick = function (){};
    }
    
        
};
  


let managerModule = new ManagerModule();
export {managerModule};




