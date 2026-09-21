package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators for product options and cart update
    private final By sizeMButton = By.xpath("//button[contains(text(), 'M') or @value='M']");
    private final By updateCartButton = By.cssSelector("button#btn-cart.btn-cart");
    private final By successMessage = By.cssSelector(".alert-success, .toast-success, .already-cart");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void selectSizeMedium() {
        try {
            WebElement sizeBtn = wait.until(ExpectedConditions.elementToBeClickable(sizeMButton));
            sizeBtn.click();
        } catch (Exception e) {
            System.out.println("Size M button not explicitly clickable or already selected.");
        }
    }

    public void clickUpdateCart() {
        try {
            WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(updateCartButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", updateBtn);
            updateBtn.click();
            
            // Brief pause for backend synchronization so the cart is not empty
            Thread.sleep(1500);
        } catch (Exception e) {
            WebElement updateBtn = driver.findElement(updateCartButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", updateBtn);
        }
    }

    public void addProductToCart() {
        selectSizeMedium();
        clickUpdateCart();
    }
}