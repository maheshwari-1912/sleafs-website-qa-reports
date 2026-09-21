package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstNameInput = By.cssSelector("#fname, input[name='fname'], input[name='first_name']");
    private final By lastNameInput = By.cssSelector("#lname, input[name='lname'], input[name='last_name']");
    private final By emailInput = By.cssSelector("#email, input[name='email']");
    private final By phoneInput = By.cssSelector("#phone, input[name='phone']");
    private final By addressInput = By.cssSelector("input[name='address']");
    private final By cityInput = By.cssSelector("input[name='city']");
    private final By zipInput = By.id("zip");
    private final By landmarkInput = By.cssSelector("input[placeholder*='Landmark']");

    private final By termsCheckbox = By.id("terms-chk");
    private final By placeOrderBtn = By.id("place-btn");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void safeType(By locator, String value) {
        try {
            WebElement elem = driver.findElement(locator);
            if (elem.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", elem);
                elem.clear();
                elem.sendKeys(value);

                ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", elem, value);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", elem);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", elem);
            }
        } catch (Exception ignored) {}
    }

    public void selectDropdownOption(String selectId, String targetValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "var sel = document.getElementById(arguments[0]); " +
            "if (sel) { " +
            "  if (sel.tomselect) { " +
            "    sel.tomselect.setValue(arguments[1]); " +
            "  } else { " +
            "    for(var i=0; i<sel.options.length; i++) { " +
            "      if(sel.options[i].text.toLowerCase().trim() === arguments[1].toLowerCase().trim() || " +
            "         sel.options[i].value.toLowerCase().trim() === arguments[1].toLowerCase().trim()) { " +
            "        sel.selectedIndex = i; " +
            "        break; " +
            "      } " +
            "    } " +
            "  } " +
            "  sel.dispatchEvent(new Event('change', { bubbles: true })); " +
            "  sel.dispatchEvent(new Event('input', { bubbles: true })); " +
            "}", selectId, targetValue
        );

        try {
            By optionLoc = By.cssSelector("div[data-value='" + targetValue + "']");
            WebElement opt = driver.findElement(optionLoc);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", opt);
            js.executeScript("arguments[0].click();", opt);
        } catch (Exception ignored) {}
    }

    public void fillGuestDetails() {
        try {
            WebElement savedAddr = driver.findElement(By.cssSelector("#shippingAddressContainer .shipping-addr-card, input[name='select_shipping_radio']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'}); arguments[0].click();", savedAddr);
        } catch (Exception ignored) {}

        safeType(firstNameInput, "celisya");
        safeType(lastNameInput, "mary");
        safeType(emailInput, "celisya" + System.currentTimeMillis() + "@gmail.com");
        safeType(phoneInput, "7339156630");
        safeType(addressInput, "63,yadhavar mela street");
        safeType(cityInput, "nagercoil");
        safeType(zipInput, "629002");
        safeType(landmarkInput, "Near landmark");

        selectDropdownOption("country", "India");
        selectDropdownOption("state", "Tamil Nadu");
    }

    public void acceptTermsAndPlaceOrder() {
        fillGuestDetails();

        wait.until(d -> {
            try {
                WebElement checkbox = d.findElement(termsCheckbox);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
                if (!checkbox.isSelected()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
                }

                WebElement button = d.findElement(placeOrderBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
                if (button.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                    return true;
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public boolean isCheckoutPageDisplayed() {
        try {
            boolean urlMatch = wait.until(ExpectedConditions.urlContains("checkout"));
            WebElement placeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderBtn));
            return urlMatch && placeBtn.isDisplayed();
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("checkout");
        }
    }
}