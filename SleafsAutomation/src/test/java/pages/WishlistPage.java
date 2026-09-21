package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WishlistPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By moveToCartBtn = By.cssSelector(".move-to-cart, button[title*='Cart'], .btn-cart");
    private final By addToWishlistBtn = By.id("btn-wish");

    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void addToWishlist() {
        try {
            WebElement btn = wait.until(d -> d.findElement(addToWishlistBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception ignored) {}
    }

    public void moveWishlistToCart() {
        try {
            WebElement btn = wait.until(d -> d.findElement(moveToCartBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            
            // Wait for backend AJAX transfer to complete
            Thread.sleep(2000);
            
            // Refresh page to ensure cart state is synchronized
            driver.navigate().refresh();
            Thread.sleep(1500);
        } catch (Exception ignored) {}
    }
}