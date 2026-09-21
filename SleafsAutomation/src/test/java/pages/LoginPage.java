package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String LOGIN_URL = "https://riyoceline.com/projects/sleafs/shop/login";

    private final By emailInput = By.id("phone");
    private final By passwordInput = By.id("login-password");
    private final By signInButton = By.cssSelector("button[type='submit'].btn-auth");
    private final By errorBanner = By.cssSelector(".alert, .error, .invalid-feedback, .alert-danger");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Always navigates fresh. CodeIgniter's CSRF token (ci_csrf_token hidden
     * field) is tied to the session and can be single-use / short-lived, so
     * every login attempt should start from a clean page load rather than
     * reusing a form that may already be stale from a prior submit.
     */
    public void open() {
        driver.get(LOGIN_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
    }

    public void enterCredentials(String email, String password) {
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        userField.clear();
        userField.sendKeys(email);

        WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passField.clear();
        passField.sendKeys(password);
    }

    public void clickSubmit() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        btn.click();
    }

    /**
     * Full flow in one call: fresh page load -> fill -> submit.
     * Use this instead of calling open()/enterCredentials()/clickSubmit()
     * separately across steps, so the CSRF token used to fill the form is
     * guaranteed to be the same one used to submit it.
     */
    public void login(String email, String password) {
        open();
        enterCredentials(email, password);
        clickSubmit();
    }

    /** True once the error banner is visible after a submit. */
    public boolean isErrorDisplayed() {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(errorBanner));
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** True once the URL has moved away from the login page (i.e. success). */
    public boolean isLoginSuccessful() {
        try {
            return wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));
        } catch (Exception e) {
            return false;
        }
    }
}
