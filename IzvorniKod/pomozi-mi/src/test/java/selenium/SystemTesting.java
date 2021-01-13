package selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class SystemTesting {

    private static String userPassword = "password";
    private static String userUsername = "username";
    private static String userPasswordWrong = "passwordWrong";
    private static String userUsernameWrong = "usernameWrong";

    private static String adminPassword = "password";
    private static String adminUsername = "admin";

    private static String authoredRequestTitle = "TestRequest";
    private static String authoredRequestTitleHasHandlers = "TestPotentialHandlers";

    private static String searchText = "korisnik";


    private WebDriver webDriver;

    @BeforeEach
    void setupEach(){
        this.webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @BeforeAll
    public static void setup(){
        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
    }


    /**
     * Web driver tries to log in with correct credentials
     * If login is successfull it is redirected to http://18.220.214.27:8080/#/requests
     */
    @Test
    public void successfulLogin(){

        try{
            login( userUsername, userPassword);

            assertTrue(webDriver.getCurrentUrl().equals("http://18.220.214.27:8080/#/requests"));

            webDriver.close();
        }
        catch(Exception e){
            fail();
            webDriver.close();
        }

    }


    /**
     * Driver tries to log in with invalid username
     * After invalid login a message about it is displayed
     */
    @Test
    public void unsuccessfulLoginInvalidUsername(){

        try{

            login( userUsernameWrong, userPassword);
            WebElement errorMessage = webDriver.findElement(By.className("api_error_message"));
            assertTrue(errorMessage.getText().equals("Korisnički podaci nisu ispravni"));

            webDriver.close();
        }
        catch(Exception e){
            fail();
            webDriver.close();
        }

    }

    /**
     * Driver tries to log in with invalid username
     * After invalid login a message about it is displayed
     */
    @Test
    public void unsuccessfulLoginInvalidPassword(){

        try{

            login( userUsername, userPasswordWrong);
            sleep(500);
            WebElement errorMessage = webDriver.findElement(By.className("api_error_message"));
            assertTrue(errorMessage.getText().equals("Korisnički podaci nisu ispravni"));

            webDriver.close();
        }
        catch(Exception e){
            fail();
            webDriver.close();
        }

    }

    /**
     * Creates a new request, tests for proper redirection after creation
     * @throws InterruptedException
     */
    @Test
    public void createRequestExpectedBehaviour() throws InterruptedException {

        login( "username", "password");

        createRequest( "Halp1", "12112021", "0345PM", "description", true);

        assertTrue(webDriver.getCurrentUrl().equals("http://18.220.214.27:8080/#/newRequest"));

        webDriver.close();

    }

    /**
     * Tryes to create a request with no title
     * Tests for a corresponding error message
     * @throws InterruptedException
     */
    @Test
    public void createRequestNoTitle() throws InterruptedException {


        login( userUsername, userPassword);

        createRequest( "", "12112021", "0345PM", "description", true);

        WebElement errorMessage = webDriver.findElement(By.className("error-message"));
        System.out.println(errorMessage.getText());
        assertTrue(errorMessage.getText().equals("Naslov je obavezan"));

        webDriver.close();

    }

    /**
     * Admin views user profile
     * Tests for presence of action buttons
     */
    @Test
    public void adminViewsProfile(){

        try {
            login( adminUsername, adminPassword);

            String userProfileUrl = "http://18.220.214.27:8080/#/user/" + userUsername;
            webDriver.get(userProfileUrl);

            //Check if proper functionality buttons are rendered
            WebElement addAdminBtn = webDriver.findElement(By.className("btn-success"));
            WebElement tempBlockBtn = webDriver.findElement(By.className("btn-warning"));
            WebElement permBlockBtn = webDriver.findElement(By.className("btn-danger"));

            assertTrue(true);
            webDriver.close();
        }
        catch (Exception e){
            fail();
            System.out.println(e.getMessage());
            webDriver.close();
        }
    }

    /**
     * User views profile
     * Tests for absence of action buttons
     */
    @Test
    public void userViewsProfile(){

        try {
            login( userUsername, userPassword);

            String userProfileUrl = "http://18.220.214.27:8080/#/user/hrvoje459";
            webDriver.get(userProfileUrl);

            sleep(500);

            try{
                WebElement addAdminBtn = webDriver.findElement(By.className("btn-success"));
                fail();
            }
            catch (NoSuchElementException e){
                try{
                    WebElement tempBlockBtn = webDriver.findElement(By.className("btn-warning"));
                    fail();
                }
                catch (NoSuchElementException e1){
                    try{
                        WebElement permBlockBtn = webDriver.findElement(By.className("btn-danger"));
                        fail();
                    }
                    catch (NoSuchElementException e2){
                        assertTrue(true);
                        webDriver.close();
                    }
                }
            }

        }
        catch (Exception ex){
            fail();
            System.out.println(ex.getMessage());
            webDriver.close();
        }
    }

    /**
     * Tests behaviour when user tries to access
     * a nonexistent profile
     */
    @Test
    public void viewNonexistentProfile(){

        try {
            login( userUsername, userPassword);

            String userProfileUrl = "http://18.220.214.27:8080/#/user/nosuchusername";
            webDriver.get(userProfileUrl);
            sleep(500);

            String message = webDriver.findElement(By.id("center")).findElement(By.tagName("div")).getText();
            Assertions.assertEquals("Korisnik ne postoji?", message);

            webDriver.close();

        }
        catch (Exception e){
            fail();
            webDriver.close();
        }
    }

    /**
     * Tests request component if
     * potential handlers exist
     */
    @Test
    public void viewAuthoredRequestWithPotentialHandlers() {

        try {
            // go to all requests
            login( userUsername, userPassword);

            // find request with title TestRequest
            List<WebElement> requestList = webDriver.findElement(By.className("list")).findElements(By.className("item"));
            List<WebElement> filteredList = requestList.stream()
                    .filter(el -> {
                        System.out.println(el.findElement(By.id("requestTitle")).getText());
                        return el.findElement(By.id("requestTitle")).getText().equals(authoredRequestTitleHasHandlers);
                    })
                    .collect(Collectors.toList());

            filteredList.get(0).findElement(By.tagName("button")).click();

            webDriver.findElement(By.className("blue")).click();
            sleep(1000);

            String modalTitle = webDriver.findElement(By.className("modal-title")).getText();

            Assertions.assertEquals("Potencijalni izvršitelji", modalTitle);

            webDriver.close();
        }
        catch (Exception e){
            fail();
            webDriver.close();
        }



    }

    /**
     * Tests request component if
     * potential handlers don't exist
     */
    @Test
    public void viewAuthoredRequest() {

        try {
            // go to all requests
            login( userUsername, userPassword);

            // find request with title TestRequest
            List<WebElement> requestList = webDriver.findElement(By.className("list")).findElements(By.className("item"));
            List<WebElement> filteredList = requestList.stream()
                    .filter(el -> {
                        System.out.println(el.findElement(By.id("requestTitle")).getText());
                        return el.findElement(By.id("requestTitle")).getText().equals(authoredRequestTitle);
                    })
                    .collect(Collectors.toList());

            filteredList.get(0).findElement(By.tagName("button")).click();

            sleep(500);

            webDriver.findElement(By.className("red"));
            assertTrue(true);

            webDriver.close();
        }
        catch (Exception e){
            fail();
            webDriver.close();
        }

    }

    /**
     * Tests request component
     * when user isn't the author
     */
    @Test
    public void viewNonAuthoredRequest() {

        try {
            // go to all requests
            login( adminUsername, adminPassword);

            // find request with title TestRequest
            List<WebElement> requestList = webDriver.findElement(By.className("list")).findElements(By.className("item"));
            List<WebElement> filteredList = requestList.stream()
                    .filter(el -> {
                        System.out.println(el.findElement(By.id("requestTitle")).getText());
                        return el.findElement(By.id("requestTitle")).getText().equals(authoredRequestTitle);
                    })
                    .collect(Collectors.toList());

            filteredList.get(0).findElement(By.tagName("button")).click();

            sleep(500);

            String buttonText = webDriver.findElement(By.className("blue")).getText();
            Assertions.assertEquals("Javi se", buttonText);

            webDriver.close();
        }
        catch (Exception e){
            fail();
            webDriver.close();
        }



    }

    /**
     * Creates a new request
     * @param title Request title to be filled
     * @param expirationDate Request expiration date
     * @param expirationTime Request expiration time
     * @param description Request description
     * @param virtual Whether request is virtual or not
     * @throws InterruptedException
     */
    private void createRequest(String title, String expirationDate, String expirationTime, String description, Boolean virtual) throws InterruptedException {
        String newRequestUrl = "http://18.220.214.27:8080/#/newRequest";
        webDriver.get(newRequestUrl);

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


    /**
     * Performs login for given parameters
     * @param username Username for login
     * @param password Password for login
     * @throws InterruptedException
     */
    private void login(String username, String password) throws InterruptedException {

        String loginUrl = "http://18.220.214.27:8080/";
        webDriver.get(loginUrl);

        WebElement usernameInput = webDriver.findElement(By.ByName.name("username"));
        usernameInput.sendKeys(username);

        WebElement passwordInput = webDriver.findElement(By.name("password"));
        passwordInput.sendKeys(password);

        WebElement buttonGroup = webDriver.findElement(By.className("loginOrRegisterBtns"));
        WebElement loginButton = buttonGroup.findElement(By.className("btn-primary"));
        loginButton.click();

        sleep(500);

        return;
    }

    /**
     * Testing of unimplemented functionality - searchbar
     */
    @Test
    public void searchbarTesting(){

        try {
            login(userUsername, userPassword);

            WebElement searchbar = webDriver.findElement(By.className("topHeader")).findElement(By.className("form-inline"));
            searchbar.findElement(By.tagName("input")).sendKeys(searchText);
            searchbar.findElement(By.tagName("button")).click();
            //test no redirection
            sleep(500);
            Assertions.assertEquals("http://18.220.214.27:8080/#/requests", webDriver.getCurrentUrl());
            webDriver.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            fail();
            webDriver.close();
        }
    }

}
