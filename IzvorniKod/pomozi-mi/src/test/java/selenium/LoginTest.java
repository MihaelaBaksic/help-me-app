package selenium;

import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.mockito.*;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {



    /**
     * Web driver tries to log in with correct credentials
     * If login is successfull it is redirected to http://3.139.60.236:8080/#/requests
     */
    @Test
    public void successfulLogin(){

        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();

        try{
            login(webDriver, "username", "password");
            sleep(1000);
            assertTrue(webDriver.getCurrentUrl().equals("http://3.139.60.236:8080/#/requests"));

            webDriver.quit();
        }
        catch(Exception e){
            fail();
            webDriver.quit();
        }

    }


    /**
     * Driver tries to log in with invalid username
     * After invalid login a message about it is displayed
     */
    @Test
    public void unsuccessfulLoginInvalidUsername(){


        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();

        try{

            login(webDriver, "usernameWrong", "password");
            sleep(1000);
            WebElement errorMessage = webDriver.findElement(By.className("api_error_message"));
            assertTrue(errorMessage.getText().equals("Korisnički podaci nisu ispravni"));

            webDriver.quit();
        }
        catch(Exception e){
            fail();
            webDriver.quit();
        }

    }

    /**
     * Driver tries to log in with invalid username
     * After invalid login a message about it is displayed
     */
    @Test
    public void unsuccessfulLoginInvalidPassword(){


        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();

        try{

            login(webDriver, "username", "passwordWrong");
            sleep(1000);
            WebElement errorMessage = webDriver.findElement(By.className("api_error_message"));
            assertTrue(errorMessage.getText().equals("Korisnički podaci nisu ispravni"));

            webDriver.quit();
        }
        catch(Exception e){
            fail();
            webDriver.quit();
        }

    }


    private void login(WebDriver webDriver, String username, String password){

        String loginUrl = "http://3.139.60.236:8080/";
        webDriver.get(loginUrl);

        WebElement usernameInput = webDriver.findElement(By.ByName.name("username"));
        usernameInput.sendKeys(username);

        WebElement passwordInput = webDriver.findElement(By.name("password"));
        passwordInput.sendKeys(password);

        WebElement buttonGroup = webDriver.findElement(By.className("loginOrRegisterBtns"));
        WebElement loginButton = buttonGroup.findElement(By.className("btn-primary"));
        loginButton.click();

        return;
    }
}