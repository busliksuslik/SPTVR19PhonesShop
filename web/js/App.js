import {authModule} from "./AuthModule.js";
import {productModule} from "./ProductModule.js";
import {userModule} from "./UserModule.js";
import {managerModule} from "./ManagerModule.js";
import {adminModule} from "./AdminModule.js";

//import {phoneModule} from './PhoneModule.js';

document.getElementById("loginForm").onclick = function (){
    toggleMenuActive("loginForm");
    authModule.printLoginForm();
};

document.getElementById("logout").onclick = function (){
    toggleMenuActive("logout");
    authModule.logout();
    
};

document.getElementById("products").onclick = function (){
    toggleMenuActive("products");
    productModule.printProducts();
};

document.getElementById("addUserForm").onclick = function (){
    toggleMenuActive("addUserForm");
    userModule.registration();
};

document.getElementById("addMoneyForm").onclick = function (){
    toggleMenuActive("addMoneyForm");
    userModule.addMoneyForm();
};

document.getElementById("addHistoryForm").onclick = function (){
    toggleMenuActive("addHistoryForm");
    productModule.addToCartForm();
};

document.getElementById("managerMode").onclick = function (){
    toggleMenuActive("managerMode");
    managerModule.printManagerMenu();
};

document.getElementById("adminMode").onclick = function (){
    toggleMenuActive("adminMode");
    adminModule.printAdminMenu();
};

document.getElementById("cart").onclick = function (){
    toggleMenuActive("cart");
    productModule.cart();
};
document.getElementById("change").onclick = function (){
    toggleMenuActive("change");
    userModule.mutationOne();
};
authModule.toogleMenu();

function toggleMenuActive(elementId){
    let activeElement = document.getElementById(elementId);
    let passiveElements = document.getElementsByClassName("nav-item");
    
    for (let i = 0; i < passiveElements.length; i++){
        if (activeElement === passiveElements[i]){
            passiveElements[i].classList.add("active-menu");
        } else{
            if(passiveElements[i].classList.contains("active-menu")){
                passiveElements[i].classList.remove("active-menu");
            }
        }
    }
}

