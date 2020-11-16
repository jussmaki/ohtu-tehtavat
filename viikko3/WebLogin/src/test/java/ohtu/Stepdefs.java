package ohtu;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {
    //WebDriver driver = new ChromeDriver();
    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567";
    
    //login feature
    @Given("login is selected")
    public void loginIsSelected() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("login"));       
        element.click();   
    }
    
    @When("correct username {string} and password {string} are given")
    public void correctUsernameAndPasswordAreGiven(String username, String password) {
        logInWith(username, password);
    }    
    
    @Then("user is logged in")
    public void userIsLoggedIn() {
        pageHasContent("Ohtu Application main page");
    }    
 
    @When("correct username {string} and incorrect password {string} are given")
    public void correctUsernameAndIncorrectPasswordAreGiven(String username, String password) {
        logInWith(username, password);
    }

    @When("nonexistent username {string} and password {string} are given")
    public void nonexistentUsernameANdPasswordAreGiven(String username, String password) {
        logInWith(username, password);
    }
    
    @Then("user is not logged in and error message is given")
    public void userIsNotLoggedInAndErrorMessageIsGiven() {
        pageHasContent("invalid username or password");
        pageHasContent("Give your credentials to login");
    }
    
    //new account feature
    @Given("command new user is selected")
    public void registerNewUserIsSelected() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
    }  
    @When("a valid username {string} and password {string} and matching password confirmation are entered")
    public void validUsernameAndPasswordAndPasswordMatchingConfirmationAreGiven(String username, String password) {
        fillAndSubmitRegisterFormWith(username, password, password);
    }
    
    @Then("a new user is created")
    public void newUserIsCreated() {
        pageHasContent("Welcome to Ohtu Application!");
    }    
    
    @When("a invalid username {string} and valid password {string} and matching password confirmation are entered")
    public void invalidUsernameAndValidPasswordANdPasswordMatchingConfirmationAreGiven(String username, String password) {
        fillAndSubmitRegisterFormWith(username, password, password);
    }

    @When("a valid username {string} and too short password {string} and matching password confirmation are entered")
    public void aValidUsernameAndTooShortPasswordAndMatchingPasswordConfirmationAreGiven(String username, String password) {
        fillAndSubmitRegisterFormWith(username, password, password);
    }
    
    @When("a valid username {string} and valid password {string} and mismatching password confirmation {string} are entered")
    public void aValidusernameAndValidPasswordAndMismatchingPasswordConfirmationAreGiven(String username, String password, String passwordConfirmation) {
        fillAndSubmitRegisterFormWith(username, password, passwordConfirmation);
    }
    
    @Then("user is not created and error {string} is reported")
    public void userIsNotCreatedAndErrorIsReported(String error) {
        pageHasContent("Create username and give password");
        pageHasContent(error);
    }
    
    @After
    public void tearDown(){
        driver.quit();
    }
        
    /* helper methods */
 
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }
        
    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Give your credentials to login"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();  
    }
    
    private void fillAndSubmitRegisterFormWith(String username, String password, String passwordConfirmation) {
        assertTrue(driver.getPageSource().contains("Create username and give password"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(passwordConfirmation);
        element = driver.findElement(By.name("signup"));
        element.submit();        
    }
}
