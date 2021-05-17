/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.webinf.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author nikita
 */
public class AdminModePage {
    protected WebDriver driver;
    private final By changeUserPropertiesForm = By.id("changeUserPropertiesForm");
    
    public AdminModePage(WebDriver driver){
        this.driver = driver;
    }
    public ChangeUserPropertiesForm getChangeUserPropertiesForm(){
        driver.findElement(changeUserPropertiesForm).click();
        return new ChangeUserPropertiesForm(driver);
    }
}
