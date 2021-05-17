/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.webinf.user;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.IndexPage;

/**
 *
 * @author nikita
 */
public class AddHistoryFormPage {
    protected WebDriver driver;
    private By quantityBy = By.xpath("//input[@name='quantity']");
    private By submitBy = By.xpath("//input[@type='submit']");
    
    public AddHistoryFormPage(WebDriver driver) {
        this.driver = driver;
    }
    public IndexPage chooseOneOfEverything(){
        List<WebElement> els = driver.findElements(quantityBy);
        els.get(0).sendKeys("1");
        driver.findElement(submitBy).click();
        return new IndexPage(driver);
    }
}
