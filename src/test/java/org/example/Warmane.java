package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v96.indexeddb.model.Key;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class Warmane {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        String chromeProfilePath = "/Users/kostyamono/Library/Application Support/Google/Chrome/";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir="+ chromeProfilePath);
        options.addArguments("--start-maximized");
        ChromeDriver driver;
        driver = new ChromeDriver(options);
        Actions actionProvider = new Actions(driver);
//        driver.get("https://www.warmane.com/account/login");
//        WebElement userID = driver.findElement(By.name("userID"));
//        userID.sendKeys(" ");
//        WebElement userPW = driver.findElement(By.name("userPW"));
//        userPW.sendKeys(" ");
//        WebElement loginWidget = driver.findElement(By.id("loginWidget"));
//        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
//        actionProvider.click(loginWidget).build().perform();
//        WebElement pushLoginButton = driver.findElement(By.className("wm-ui-btn"));
//        actionProvider.click(pushLoginButton);
//        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));
        driver.get("https://www.warmane.com/account/trade");
        WebElement typeRealmName = driver.findElement(By.id("realmselector_titleText"));
        WebElement realmMenuClick = driver.findElement(By.id("realmselector_msdd"));
        actionProvider.click(realmMenuClick).build().perform();
        realmMenuClick.sendKeys(Keys.COMMAND);
        typeRealmName.sendKeys("Frostwolf" + Keys.ENTER);
    }
}
