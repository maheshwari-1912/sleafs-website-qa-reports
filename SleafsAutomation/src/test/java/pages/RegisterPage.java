package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By fnameField = By.cssSelector("#fname, input[name='fname'], input[name='first_name']");
    private final By lnameField = By.cssSelector("#lname, input[name='lname'], input[name='last_name']");
    private final By emailField = By.cssSelector("#email, input[name='email']");
    private final By phoneField = By.cssSelector("#phone, input[name='phone'], input[name='mobile']");
    private final By passwordField = By.cssSelector("#password, input[name='password']");
    private final By confirmPasswordField = By.cssSelector("#confirm_password, input[name='confirm_password']"); // Updated locator
    private final By registerBtn = By.cssSelector("#registerBtn, button[type='submit'], input[type='submit'], .register-btn, #submit");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public void open() {
        driver.get("https://riyoceline.com/projects/sleafs/shop/register");
    }

    private void typeValue(By locator, String value) {
        try {
            WebElement elem = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", elem);
            elem.clear();
            elem.sendKeys(value);
        } catch (Exception ignored) {}
    }

    public void registerAccount(String fname, String lname, String email, String phone, String password) {
        typeValue(fnameField, fname);
        typeValue(lnameField, lname);
        typeValue(emailField, email);
        typeValue(phoneField, phone);
        typeValue(passwordField, password);
        typeValue(confirmPasswordField, password); // Will now successfully fill the confirm password field

        // Click Register button via JS
        try {
            WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(registerBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception ignored) {}

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    // Overloaded helper method to match CheckoutSteps invocation seamlessly
    public void register(String fname, String lname, String email, String password) {
        registerAccount(fname, lname, email, "1234567890", password);
    }
}