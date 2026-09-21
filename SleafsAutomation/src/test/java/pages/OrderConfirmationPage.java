package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderConfirmationPage {

    private final WebDriverWait wait;

    public OrderConfirmationPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isConfirmationPageDisplayed() {
        try {
            return wait.until(d -> d.getCurrentUrl().contains("order") 
                                || d.getCurrentUrl().contains("thank")
                                || !d.findElements(By.xpath("//*[contains(text(),'Thank You') or contains(text(),'Order')]")).isEmpty());
        } catch (Exception e) {
            return false;
        }
    }
}