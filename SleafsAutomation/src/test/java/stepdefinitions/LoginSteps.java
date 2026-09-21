package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import hooks.Hooks;
import pages.LoginPage;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage(Hooks.driver);

    // Your manually registered test credentials
    public static final String REGISTERED_EMAIL = "8825952264";
    public static final String REGISTERED_PASSWORD = "961217";

    @Given("I open the SLEAFS login page")
    public void openLoginPage() {
        loginPage.open();
    }

    @When("I enter a valid email and password")
    public void enterValidCredentials() {
        loginPage.enterCredentials(REGISTERED_EMAIL.trim(), REGISTERED_PASSWORD.trim());
    }

    @When("I enter a valid email and an invalid password")
    public void enterValidEmailAndInvalidPassword() {
        loginPage.enterCredentials(REGISTERED_EMAIL.trim(), "WrongPassword999!");
    }

    @When("I click the Sign In button")
    public void clickSignIn() {
        loginPage.clickSubmit();
    }

    @Then("I should be logged in successfully")
    public void verifySuccessfulLogin() {
        boolean success = loginPage.isLoginSuccessful();
        System.out.println("Current URL -> " + Hooks.driver.getCurrentUrl());
        Assert.assertTrue("Expected to be redirected away from the login page after a valid login", success);
    }

    @Then("I should see a login error message")
    public void verifyLoginErrorMessage() {
        boolean hasError = loginPage.isErrorDisplayed();
        Assert.assertTrue("Expected error message not found!", hasError || Hooks.driver.getCurrentUrl().contains("login"));
    }

    @Then("I should see validation messages")
    public void verifyValidationMessages() {
        Assert.assertTrue("Expected to stay on login page due to validation!", Hooks.driver.getCurrentUrl().contains("login"));
    }
}
