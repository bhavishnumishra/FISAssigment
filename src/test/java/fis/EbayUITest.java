package fis;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.webdriver.WebDriverBrowser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class EbayUITest {

    @Test
    public  void addToCart()  {
        //Browser setup and launch url
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.ebay.com");

        driver.manage().window().maximize();

        //Search for 'book'
        WebElement searchBox = driver.findElement(By.id("gh-ac"));
        searchBox.sendKeys("book");

        WebElement searchButton = driver.findElement(By.id("gh-btn"));
        searchButton.click();

        //Click on the first book in the list

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement firstItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='srp-results srp-list clearfix']//li[1]//a[@class='s-item__link']")));
        firstItem.click();

        //Switch window

        Set<String> allwindows=driver.getWindowHandles();
        String currentWindow=driver.getWindowHandle();
        System.out.println("current: "+currentWindow);
        for(String windowid: allwindows)
        {
            if(!windowid.equals(currentWindow)){
                driver.switchTo().window(windowid);
                break;
            }

        }

        // On the item listing page, click 'Add to cart'
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Add to cart']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);

        //verify item is added to cart
        WebElement cartCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#gh-cart-n")));
        int cartCount = Integer.parseInt(cartCountElement.getText());
        Assert.assertEquals(cartCount,1,"Item is not added to cart");



    }

}
