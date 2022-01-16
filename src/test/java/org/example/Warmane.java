package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Warmane {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver;
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        Actions actionProvider = new Actions(driver);
        driver.get("https://www.warmane.com/account/login");
        WebElement userID = driver.findElement(By.name("userID"));
        userID.sendKeys("charm888");
        WebElement userPW = driver.findElement(By.name("userPW"));
        userPW.sendKeys("qwerty123");
        WebElement loginWidget = driver.findElement(By.id("loginWidget"));
        actionProvider.click(loginWidget).build().perform();
        WebElement pushLoginButton = driver.findElement(By.className("wm-ui-btn"));
        actionProvider.click(pushLoginButton).build().perform();
    }
}
