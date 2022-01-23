package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Warmane {
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        String chromeProfilePath = "/Users/kostyamono/Library/Application Support/Google/Chrome/";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + chromeProfilePath);
        ChromeDriver driver;
        driver = new ChromeDriver(options);
        Actions actionProvider = new Actions(driver);
        WebDriverWait waitToBeClickable = new WebDriverWait(driver, 30);
        FileWriter writer = new FileWriter("WarmaneStats.txt", true);


        driver.get("https://www.warmane.com/account/trade");
        while (true) {
            Date date = new Date();

            WebElement typeRealmName = driver.findElement(By.xpath("//*[@id=\"realmselector_titleText\"]"));
            waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.id("realmselector_msdd")));
            WebElement realmMenuClick = driver.findElement(By.id("realmselector_msdd"));
            actionProvider.click(realmMenuClick).build().perform();
            realmMenuClick.sendKeys(Keys.COMMAND);
            typeRealmName.sendKeys("Icecrown" + Keys.ENTER);


            waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"characterselector_msdd\"]")));
            WebElement charMenu = driver.findElement(By.xpath("//*[@id=\"characterselector_msdd\"]/div[1]"));
            charMenu.click();
            waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"characterselector_child\"]/ul/li[2]/span")));
            WebElement charMenuClick = driver.findElement(By.xpath("//*[@id=\"characterselector_child\"]/ul/li[2]/span"));
            charMenuClick.click();

            waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"serviceselector_msdd\"]/div[1]")));
            WebElement serviceMenu = driver.findElement(By.xpath("//*[@id=\"serviceselector_msdd\"]/div[1]"));
            serviceMenu.click();
            waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"serviceselector_child\"]/ul/li[3]/span")));
            WebElement serviceMenuClick = driver.findElement(By.xpath("//*[@id=\"serviceselector_child\"]/ul/li[3]/span"));
            serviceMenuClick.click();

            waitToBeClickable.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"page-block\"]/div/div[1]/div[2]/div/div/div[3]/p")));
            WebElement selectGold = driver.findElement(By.xpath("//*[@id=\"page-block\"]/div/div[1]/div[2]/div/div/div[3]/p"));
            Thread.sleep(1000);
            selectGold.click();
            Thread.sleep(2000);

            WebDriverWait waitVisibilityOfElement = new WebDriverWait(driver, 20);
            waitVisibilityOfElement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"data-table\"]/tbody/tr[*]/td[2]/table/tbody/tr/td/a/span")));
            List<WebElement> findGoldCells = driver.findElements(By.xpath("//*[@id=\"data-table\"]/tbody/tr[*]/td[2]/table/tbody/tr/td/a/span"));

            waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"page-block\"]/div/div[2]/div/div/div[2]/a[2]")));
            WebElement scrollDownArrow = driver.findElement(By.xpath("//*[@id=\"page-block\"]/div/div[2]/div/div/div[2]/a[2]"));

            List<String> GoldPerCoins = new ArrayList<>();
            List<Integer> goldPerCoinList = new ArrayList<>();
            int maxGoldPerOneCoin = 0;
            int maxGoldPerOneCoinIndex = 0;
            int minGoldPerOneCoin = 100000;
            int minGoldPerOneCoinIndex = 0;
            String maxGoldPerCoins = "";
            String minGoldPerCoins = "";
            for (int i = 1; i <= findGoldCells.size(); i++) {
                waitVisibilityOfElement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"data-table\"]/tbody/tr[" + i + "]/td[2]")));
                WebElement getGoldValue = driver.findElement(By.xpath("//*[@id=\"data-table\"]/tbody/tr[" + i + "]/td[2]/table/tbody/tr/td/a/span"));
                WebElement getCoinsValue = driver.findElement(By.xpath("//*[@id=\"data-table\"]/tbody/tr[" + i + "]/td[6]/span"));
                int goldPerCoin = Integer.parseInt(getGoldValue.getText()) / Integer.parseInt(getCoinsValue.getText());
                goldPerCoinList.add(goldPerCoin);
                GoldPerCoins.add(getGoldValue.getText() + " gold - " + getCoinsValue.getText() + " coins");
                if (goldPerCoinList.get(i - 1) > maxGoldPerOneCoin) {
                    maxGoldPerOneCoin = goldPerCoinList.get(i - 1);
                    maxGoldPerOneCoinIndex = i;
                    maxGoldPerCoins = GoldPerCoins.get(i - 1);
                }
                if (goldPerCoinList.get(i - 1) < minGoldPerOneCoin) {
                    minGoldPerOneCoin = goldPerCoinList.get(i - 1);
                    minGoldPerOneCoinIndex = i;
                    minGoldPerCoins = GoldPerCoins.get(i - 1);
                }
                System.out.println(i + ") " + GoldPerCoins.get(i - 1) + "     " + goldPerCoin + " gold/coin");
                scrollDownArrow.click();
                if (i % 3 == 0) {
                    scrollDownArrow.click();
                }
            }
            String maxResult = "Maximum at: " + maxGoldPerOneCoinIndex + ") " + maxGoldPerCoins + " (" + maxGoldPerOneCoin + " gold/coin)";
            String minResult = "Minimum at: " + minGoldPerOneCoinIndex + ") " + minGoldPerCoins + " (" + minGoldPerOneCoin + " gold/coin)";
            System.out.println(maxResult + "\n" + minResult);


            writer.write("\n" + date + "\n" + maxResult + "\n" + minResult);
            writer.flush();

            Thread.sleep(3600000);
            driver.navigate().refresh();
        }
    }
}
