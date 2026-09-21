package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By checkoutBtn = By.cssSelector("#proceedCheckoutBtn, a[href*='checkout'], button.checkout-btn, .btn-checkout, a.btn-checkout");
    private final By cartItems = By.cssSelector(".cart-item, tr.cart_item, .product-name, .cart-product");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void proceedToCheckout() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(cartItems));
        } catch (Exception ignored) {}

        try {
            Thread.sleep(2500);
        } catch (InterruptedException ignored) {}

        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception e) {
            driver.get("https://riyoceline.com/projects/sleafs/shop/checkout");
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {}
        
        if (!driver.getCurrentUrl().contains("checkout")) {
            driver.get("https://riyoceline.com/projects/sleafs/shop/checkout");
        }
    }
}