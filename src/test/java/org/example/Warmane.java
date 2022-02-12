package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Warmane {

    // TODO: remove statics
    private static ChromeDriver driver;
    private static WebDriverWait waitToBeClickable;
    private static int globalMaxGoldPerOneCoin = 0;

    private static void init() {
        WebDriverManager.chromedriver().setup();
        String chromeProfilePath = System.getenv("GD_PATH");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + chromeProfilePath);
        driver = new ChromeDriver(options);
        waitToBeClickable = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        init();
        if (driver == null || waitToBeClickable == null) {
            throw new IllegalStateException("couldn't initialize");
        }

        while (true) {
            try {
                fetchData();
            } catch (RuntimeException e) {
                System.out.println("couldn't fetch data " + e.getMessage());
            }
        }
    }

    public static void fetchData() throws InterruptedException, IOException {
        Thread.sleep(5_000);
        driver.get("https://www.warmane.com/account/trade");
        driver.navigate().refresh();

        waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath
            ("//*[@id=\"realmselector_title\"]")));
        WebElement realmMenu = driver.findElement(By.xpath
            ("//*[@id=\"realmselector_title\"]"));
        realmMenu.click();
        waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath
            ("//*[@id=\"realmselector_child\"]/ul/li[4]/span")));
        WebElement realmMenuClick = driver.findElement(By.xpath("//*[@id=\"realmselector_child\"]/ul/li[4]/span"));
        realmMenuClick.click();

        waitToBeClickable.until(ExpectedConditions.elementToBeClickable
            (By.xpath("//*[@id=\"characterselector_msdd\"]")));
        WebElement charMenu = driver.findElement(By.xpath("//*[@id=\"characterselector_msdd\"]/div[1]"));
        charMenu.click();
        waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath
            ("//*[@id=\"characterselector_child\"]/ul/li[2]/span")));
        WebElement charMenuClick = driver.findElement(By.xpath
            ("//*[@id=\"characterselector_child\"]/ul/li[2]/span"));
        charMenuClick.click();

        waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath
            ("//*[@id=\"serviceselector_msdd\"]/div[1]")));
        WebElement serviceMenu = driver.findElement(By.xpath("//*[@id=\"serviceselector_msdd\"]/div[1]"));
        serviceMenu.click();
        waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath
            ("//*[@id=\"serviceselector_child\"]/ul/li[3]/span")));
        WebElement serviceMenuClick = driver.findElement(By.xpath
            ("//*[@id=\"serviceselector_child\"]/ul/li[3]/span"));
        serviceMenuClick.click();

        waitToBeClickable.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
            ("//*[@id=\"page-block\"]/div/div[1]/div[2]/div/div/div[3]/p")));
        WebElement selectGold = driver.findElement(By.xpath
            ("//*[@id=\"page-block\"]/div/div[1]/div[2]/div/div/div[3]/p"));
        Thread.sleep(1000);
        selectGold.click();
        Thread.sleep(2000);

        WebDriverWait waitVisibilityOfElement = new WebDriverWait(driver, 20);
        waitVisibilityOfElement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
            ("//*[@id=\"data-table\"]/tbody/tr[*]/td[2]/table/tbody/tr/td/a/span")));
        List<WebElement> findGoldCells = driver.findElements(By.xpath
            ("//*[@id=\"data-table\"]/tbody/tr[*]/td[2]/table/tbody/tr/td/a/span"));

        waitToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath
            ("//*[@id=\"page-block\"]/div/div[2]/div/div/div[2]/a[2]")));
        WebElement scrollDownArrow = driver.findElement(By.xpath
            ("//*[@id=\"page-block\"]/div/div[2]/div/div/div[2]/a[2]"));

        List<String> GoldPerCoins = new ArrayList<>();
        List<Integer> goldPerCoinList = new ArrayList<>();

        int maxGoldPerOneCoin = 0;
        int maxGoldPerOneCoinIndex = 0;
        String maxGoldPerCoins = "";

        for (int i = 1; i <= findGoldCells.size(); i++) {
            waitVisibilityOfElement.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//*[@id=\"data-table\"]/tbody/tr[" + i + "]/td[2]")));
            WebElement getGoldValue = driver.findElement(By.xpath
                ("//*[@id=\"data-table\"]/tbody/tr[" + i + "]/td[2]/table/tbody/tr/td/a/span"));
            WebElement getCoinsValue = driver.findElement
                (By.xpath("//*[@id=\"data-table\"]/tbody/tr[" + i + "]/td[6]/span"));
            int goldPerCoin = Integer.parseInt(getGoldValue.getText()) / Integer.parseInt(getCoinsValue.getText());
            goldPerCoinList.add(goldPerCoin);
            GoldPerCoins.add(getGoldValue.getText() + " gold - " + getCoinsValue.getText() + " coins");

            if (goldPerCoinList.get(i - 1) > maxGoldPerOneCoin) {
                maxGoldPerOneCoin = goldPerCoinList.get(i - 1);
                maxGoldPerOneCoinIndex = i;
                maxGoldPerCoins = GoldPerCoins.get(i - 1);
            }

            //System.out.println(i + ") " + GoldPerCoins.get(i - 1) + "     " + goldPerCoin + " gold/coin");
            scrollDownArrow.click();
            if (i % 3 == 0) {
                scrollDownArrow.click();
            }
        }


        if (globalMaxGoldPerOneCoin < maxGoldPerOneCoin) {
            globalMaxGoldPerOneCoin = maxGoldPerOneCoin;

            String newGlobalMaxToString = "\uD83C\uDD95 New global max per one coin: " + globalMaxGoldPerOneCoin;
            Runtime.getRuntime().exec(new String[]{"telegram-send", newGlobalMaxToString});
        }

        String maxResult = "\uD83D\uDFE2 Maximum at line " + maxGoldPerOneCoinIndex + ": " + maxGoldPerCoins +
            " (" + maxGoldPerOneCoin + " gold/coin)";

        String globalMaxToString = "⭕️ Global max per one coin: " + globalMaxGoldPerOneCoin;

        Date date = new Date();
        FileWriter writer = new FileWriter("WarmaneStats.txt", true);

        writer.write(System.lineSeparator() + date + System.lineSeparator() + maxResult
            + System.lineSeparator() + globalMaxToString);
        writer.flush();
    }
}
