/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import web.IndexPage;
import web.webinf.admin.AdminModePage;
import web.webinf.admin.ChangeUserPropertiesForm;
import web.webinf.header.MenuPage;
import web.webinf.login.AddUserFormPage;
import web.webinf.login.LoginFormPage;
import web.webinf.user.AddHistoryFormPage;
import web.webinf.user.CartPage;

/**
 *
 * @author nikita
 */
public class BuyingTest {
    static private WebDriver driver;
    private String login = "Test";
    private String password = "test";
    public BuyingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/SPTVR19PhonesShop");
        driver.manage().window().maximize();
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
    public void accessTest(){
        MenuPage menuPage = new MenuPage(driver);
        try{
            menuPage.getAddHistoryFormPage();
        } catch (Exception e){
            Assert.assertTrue(true);
        }
    }
    @Test 
    public void regestrationTest(){
        MenuPage menuPage = new MenuPage(driver);
        AddUserFormPage addUserFormPage = menuPage.getAddUserFormPage();
        IndexPage indexPage = addUserFormPage.registerUser(login, password);
        if(!("пользователь создан".equals(indexPage.getMassageInfo()) || "пользователь уже существует".equals(indexPage.getMassageInfo()))){
            Assert.fail("Registration error!");
        }
    }
    @Test
    public void buyWithoutMoneyTest(){
        MenuPage menuPage = new MenuPage(driver);
        LoginFormPage loginFormPage = menuPage.getLoginFormPage();
        IndexPage indexPage = loginFormPage.loginValidUser(login, password);
        if(!"success".equals(indexPage.getMassageInfo())){
            Assert.fail("login error!");
        }
        AddHistoryFormPage addHistoryFormPage;
        addHistoryFormPage = menuPage.getAddHistoryFormPage();
        indexPage = addHistoryFormPage.chooseOneOfEverything();
        if(!"Добавлено в корзину".equals(indexPage.getMassageInfo()) ){
            Assert.fail("Purchase error!");
        }
        CartPage cartPage = menuPage.getCartPage();
        cartPage.buy();
        Assert.assertEquals("Недостаточно денег", cartPage.getMassageInfo());
        
    }
}
