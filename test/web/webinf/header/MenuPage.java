/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.webinf.header;

import web.webinf.login.LoginFormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import web.webinf.admin.AdminModePage;
import web.webinf.login.AddUserFormPage;
import web.webinf.user.AddHistoryFormPage;
import web.webinf.user.CartPage;

/**
 *
 * @author nikita
 */
public class MenuPage {
    protected WebDriver driver;
    private final By loginFormBy = By.id("loginForm");
    private final By adminModeBy = By.id("adminMode");
    private final By addHistoryFormBy = By.id("addHistoryForm");
    private final By addUserFormBy = By.id("addUserForm");
    private final By cartBy = By.id("cart");
    
    
    public MenuPage(WebDriver driver){
        this.driver = driver;
    }

    public LoginFormPage getLoginFormPage() {
        driver.findElement(loginFormBy).click();
        return new LoginFormPage(driver);
    }

    public AdminModePage getAdminModePage() {
        driver.findElement(adminModeBy).click();
        return new AdminModePage(driver);
    }
    public AddHistoryFormPage getAddHistoryFormPage(){
        driver.findElement(addHistoryFormBy).click();
        return new AddHistoryFormPage(driver);
    }

    public AddUserFormPage getAddUserFormPage() {
        driver.findElement(addUserFormBy).click();
        return new AddUserFormPage(driver);
    }

    public CartPage getCartPage() {
        driver.findElement(cartBy).click();
        return new CartPage(driver);
    }

}
