package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MenPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public MenPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public ProductPage openProduct(String productName) {
        By imgLocator = By.xpath("//img[normalize-space(@alt)='" + productName + "']");

        List<WebElement> all = driver.findElements(imgLocator);
        System.out.println("[openProduct] '" + productName + "' total matches: " + all.size());
        for (WebElement el : all) {
            System.out.println("  displayed=" + el.isDisplayed());
        }

        WebElement img = wait.until(driver1 -> {
            List<WebElement> found = driver1.findElements(imgLocator);
            for (WebElement el : found) {
                if (el.isDisplayed()) return el;
            }
            return null;
        });

        WebElement productLink = img.findElement(By.xpath("./ancestor::a[1]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", productLink);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productLink);

        return new ProductPage(driver);
    }
}