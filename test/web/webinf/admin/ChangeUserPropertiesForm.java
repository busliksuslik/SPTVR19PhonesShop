/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.webinf.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author nikita
 */
public class ChangeUserPropertiesForm {
    protected WebDriver driver;
    private final By userSelectBy = By.name("userId");
    public ChangeUserPropertiesForm(WebDriver driver){
        this.driver = driver;
    }
    public boolean findAdmin(){
        Select selectUsers = new Select(driver.findElement(userSelectBy));
        try{
            selectUsers.selectByVisibleText("Логин: admin. Роль: ADMIN MANAGER CUSTOMER ");
        } catch (Exception e){
            return false;
        }
        return true; 
    }   
}
