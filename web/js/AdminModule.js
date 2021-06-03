import {userModule} from "./UserModule.js";
class AdminModule{
    printAdminMenu(){
        document.getElementById("content").innerHTML = `
            <a class="btn btn-primary" id="changeUserRoles"        role="button">ChangeUserRoles</a>`;
        document.getElementById("changeUserRoles").addEventListener('click', function(){
            userModule.changeUserRoleForm();});
    }
}

let adminModule = new AdminModule();
export {adminModule};


