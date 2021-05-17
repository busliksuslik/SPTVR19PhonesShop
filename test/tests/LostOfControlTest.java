/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import web.IndexPage;
import web.webinf.admin.AdminModePage;
import web.webinf.admin.ChangeUserPropertiesForm;
import web.webinf.header.MenuPage;
import web.webinf.login.LoginFormPage;

/**
 *
 * @author nikita
 */
public class LostOfControlTest {
    static private WebDriver driver;
    public LostOfControlTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/SPTVR19PhonesShop");
    }
    
    @AfterClass
    public static void tearDownClass() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void lostOfControlTest(){
        MenuPage menuPage = new MenuPage(driver);
        LoginFormPage loginFormPage = menuPage.getLoginFormPage();
        IndexPage indexPage = loginFormPage.loginValidUser("admin","admin");
        if(!"success".equals(indexPage.getMassageInfo())){
            Assert.fail("Login error!");
        }
        AdminModePage adminModePage = menuPage.getAdminModePage();
        ChangeUserPropertiesForm changeUserPropertiesForm = adminModePage.getChangeUserPropertiesForm();
        Assert.assertEquals(changeUserPropertiesForm.findAdmin(), false);
    }
}
