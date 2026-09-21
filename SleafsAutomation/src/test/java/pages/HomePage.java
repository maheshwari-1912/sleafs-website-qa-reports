package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartButton = By.xpath("//a[contains(@href, 'cart') or contains(@class, 'cart')]");
    private final By wishlistButton = By.xpath("//a[contains(@href, 'wishlist') or contains(@class, 'wishlist')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openHome() {
        // Exact server index path matching your login redirect
        driver.get("https://riyoceline.com/projects/sleafs/index.php/shop");
    }

    public void openCategory(String categoryName) {
        navigateToCategory(categoryName);
    }

    public void openProduct(String productName) {
        By imgLocator = By.xpath("//img[normalize-space(@alt)='" + productName + "']");
        try {
            WebElement img = wait.until(d -> {
                for (WebElement el : d.findElements(imgLocator)) {
                    if (el.isDisplayed()) return el;
                }
                return null;
            });
            WebElement productLink = img.findElement(By.xpath("./ancestor::a[1]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", productLink);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productLink);
        } catch (Exception e) {
            WebElement fallback = driver.findElement(By.xpath("//*[normalize-space(text())='" + productName + "']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fallback);
        }
    }

    public void navigateToCategory(String categoryName) {
        By categoryLink = By.xpath("//a[normalize-space()='" + categoryName + "' or contains(text(),'" + categoryName + "')]");
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(categoryLink));
            element.click();
        } catch (Exception e) {
            WebElement element = driver.findElement(categoryLink);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void addDirectToCart(String productName) {
        By directAddToCart = By.xpath("//div[contains(., '" + productName + "')]//button[contains(translate(text(),'ADD','add'),'add') or contains(@class,'cart')]");
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(directAddToCart));
            element.click();
        } catch (Exception e) {
            WebElement element = driver.findElement(directAddToCart);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public CartPage openCart() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(cartButton));
            element.click();
        } catch (Exception e) {
            WebElement element = driver.findElement(cartButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
        return new CartPage(driver);
    }

    public WishlistPage openWishlist() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(wishlistButton));
            element.click();
        } catch (Exception e) {
            WebElement element = driver.findElement(wishlistButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
        return new WishlistPage(driver);
    }
}