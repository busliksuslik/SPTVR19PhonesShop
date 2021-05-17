/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.webinf.user;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import web.IndexPage;

/**
 *
 * @author nikita
 */
public class CartPage {
    private final WebDriver driver;
    private final By submitBy = By.id("submit");
    private final By infoBy = By.id("info");
    
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void buy (){
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(submitBy)).click().perform();
    }

    public String getMassageInfo(){
        return driver.findElement(infoBy).getText();
    }
}
