package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tester {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:4567");

        //sleep(2);
        
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
        //sleep(2);
        String name = "pekka"+new Random().nextInt(100000);
        element = driver.findElement(By.name("username"));
        element.sendKeys(name);
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("signup"));
        //sleep(2);
        element.submit();
        element = driver.findElement(By.linkText("continue to application mainpage"));
        //sleep(2);
        element.click();
        element = driver.findElement(By.linkText("logout"));
        //sleep(2);
        element.click();
        //sleep(2);
        element = driver.findElement(By.linkText("login"));
        element.click();
        element = driver.findElement(By.name("username"));
        element.sendKeys(name);
        element = driver.findElement(By.name("password"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("login"));
        //sleep(2);
        element.submit();
        element = driver.findElement(By.name("username"));
        element.sendKeys(name);
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("login"));
        //sleep(2);
        element.submit();        
        //sleep(3);
        driver.quit();
    }
    
    private static void sleep(int n){
        try{
            Thread.sleep(n*1000);
        } catch(Exception e){}
    }
}
