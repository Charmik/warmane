package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

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
        typeRealmName.sendKeys("Icecrown" + Keys.ENTER);

        WebElement typeCharName = driver.findElement(By.id("characterselector_titleText"));
        WebElement charMenuClick = driver.findElement(By.id("characterselector_msdd"));
        actionProvider.click(charMenuClick).build().perform();
        charMenuClick.sendKeys(Keys.COMMAND);
        typeCharName.sendKeys("Charmdestr" + Keys.ENTER);

        WebElement typeServiceName = driver.findElement(By.id("serviceselector_titleText"));
        WebElement ServiceMenuClick = driver.findElement(By.id("serviceselector_msdd"));
        actionProvider.click(ServiceMenuClick).build().perform();
        ServiceMenuClick.sendKeys(Keys.COMMAND);
        typeServiceName.sendKeys("Item Trade" + Keys.ENTER);

    }
}
