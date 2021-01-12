package selenium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.validation.constraints.AssertTrue;
import java.util.Calendar;
import java.util.Date;




@RunWith(MockitoJUnitRunner.class)
public class RequestsTest {


    @Test
    public void createRequestExpectedBehaviour() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(webDriver, 5);

        login(webDriver, "username", "password");

        sleep(500);

        createRequest(webDriver, "Halp1", "12112021", "0345PM", "description", true);

        assertTrue(webDriver.getCurrentUrl().equals("http://3.139.60.236:8080/#/newRequest"));
        sleep(500);
        webDriver.close();

    }

    @Test
    public void createRequestNoTitle() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(webDriver, 5);

        login(webDriver, "username", "password");

        sleep(500);

        createRequest(webDriver, "", "12112021", "0345PM", "description", true);

        WebElement errorMessage = webDriver.findElement(By.className("error-message"));
        System.out.println(errorMessage.getText());
        assertTrue(errorMessage.getText().equals("Naslov je obavezan"));
        sleep(500);
        webDriver.close();

    }

    private void createRequest(WebDriver webDriver, String title, String expirationDate, String expirationTime, String description, Boolean virtual) throws InterruptedException {
        String newRequestUrl = "http://3.139.60.236:8080/#/newRequest";
        webDriver.get(newRequestUrl);

        sleep(500);

        webDriver.findElement(By.id("title")).sendKeys(title);
        WebElement dateBox= webDriver.findElement(By.id("expirationDate"));
        dateBox.sendKeys(expirationDate);
        //Press tab to shift focus to time field
        dateBox.sendKeys(Keys.TAB);
        //Fill time
        dateBox.sendKeys(expirationTime);
        webDriver.findElement(By.id("requestFormDescription")).findElement(By.name("description")).sendKeys(description);
        if (virtual) {
            webDriver.findElement(By.id("virtualniZahtjev")).click();
        }

        WebElement buttonGroup = webDriver.findElement(By.className("loginOrRegisterBtns"));
        WebElement submitButton = buttonGroup.findElement(By.className("btn-primary"));
        submitButton.click();

        sleep(500);
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
