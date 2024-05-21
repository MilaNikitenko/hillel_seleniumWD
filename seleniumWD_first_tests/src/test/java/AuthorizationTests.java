/**
 * Provides classes and methods for testing the login functionality.
 * Includes tests for user login, social login, and password recovery.
 *  @author Liudmyla Nikitenko
 */

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.RecoverPasswordPage;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static pages.LoginPage.*;
import static pages.RecoverPasswordPage.*;

/**
 * This class represents the Login Page of the application.
 * It contains methods for setting up the WebDriver, opening the login page,
 * and performing various login-related tests.
 */

public class AuthorizationTests extends BaseTest {
    /**
     * Verifies the title of the login page.
     * This test checks if the title matches the expected value.
     */
    @Test(priority = 1)
    public void checkLoginPageTitle(){
        String actualTitle = driver.getTitle();
        assertEquals("signNow Login", actualTitle);
        // AssertJ
        assertThat(actualTitle)
                .isEqualTo("signNow Login")
                .isNotEmpty()
                .startsWith("signNow");
    }

    /**
     * Checks the functionality of the Facebook social login button.
     */
    @Test(priority = 2)
    public void checkFacebookSocialLoginButton(){
        Actions action = new Actions(driver);

        WebElement facebookButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(FACEBOOK_BUTTON))));
        assertThat(facebookButton.isDisplayed()).isTrue();

        action.moveToElement(facebookButton).click().build().perform();
        wait.until(ExpectedConditions.urlContains("https://www.facebook.com/"));

        String currentURL = driver.getCurrentUrl();
        assertThat(currentURL)
                .startsWith("https://www.facebook.com/")
                .contains("facebook");
    }

    /**
     * Checks the functionality of the Google social login button.
     */
    @Test(priority = 3)
    public void checkGoogleSocialLoginButton(){
        WebElement googleButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(GOOGLE_BUTTON)));
        assertTrue(googleButton.isDisplayed());

        loginPageHelper.clickOnWebElement(googleButton);
        wait.until(ExpectedConditions.urlContains("https://accounts.google.com/"));

        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.startsWith("https://accounts.google.com/"));
    }

    /**
     * Checks the functionality of the "Forgot Password" feature.
     * This test verifies if the user can successfully request password recovery.
     * It clicks on the "Forgot Password" button, fills in the email field with
     * a valid email address, submits the form, and verifies the success message.
     * Additionally, it ensures that the user is redirected to the password recovery page.
     */
    @Test(priority = 4)
    public void checkForgotPasswordFunctionality(){
        recoverPasswordPage = new RecoverPasswordPage(driver);

        Actions action = new Actions(driver);

        WebElement forgotPasswordButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(FORGOT_PASSWORD_BUTTON)));
        assertThat(forgotPasswordButton.isDisplayed()).isTrue();

        action.moveToElement(forgotPasswordButton).click().build().perform();
        wait.until(ExpectedConditions.urlContains("/rctapp/recover-password"));

        String currentURL = driver.getCurrentUrl();
        assertThat(currentURL).endsWith("/rctapp/recover-password");

        WebElement forgotEmailField = wait.until(ExpectedConditions.visibilityOfElementLocated(FORGOT_EMAIL_FIELD));
        action.sendKeys(forgotEmailField, "mila.s.nikitenko@gmail.com").build().perform();

        WebElement recoverPasswordButton = wait.until(ExpectedConditions.visibilityOfElementLocated(RECOVER_PASSWORD_BUTTON));
        action.moveToElement(recoverPasswordButton).click().build().perform();

        WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_ALERT));
        String successAlertText = successAlert.getText();
        assertThat(successAlertText).
                isEqualTo("Great job! Just check your Inbox.");
    }

    /**
     * Checks the functionality of the "Sign Up" button.
     * This test verifies if the user can open the registration page
     * by clicking on the "Sign Up" button. It clicks on the "Sign Up" button,
     * switches to the new window, and verifies the URL of the opened page.
     * Additionally, it ensures that the URL contains specific parameters
     * indicating the type of registration page.
     */
    @Test(priority = 5)
    public void checkRegistrationPageOpening(){
        Actions action = new Actions(driver);

        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(SIGNUP_BUTTON)));
        assertThat(signUpButton.isDisplayed()).isTrue();

        action.moveToElement(signUpButton).click().build().perform();

        Set <String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            driver.switchTo().window(handle);
        }

        String currentURL = driver.getCurrentUrl();
        assertThat(currentURL)
                .endsWith("/purchase/business_free_trial/loggedout")
                .contains("snseats");
    }

    /**
     * Checks the successful login functionality.
     * This test verifies if the user can login successfully
     * with valid email and password.
     */
    @Test(priority = 6)
    public void checkSuccessLogin(){
        loginPageHelper.fillEmailOnLoginPage();
        loginPageHelper.fillPasswordOnLoginPage(PASSWORD_FIELD);
        loginPageHelper.clickOnLoginButton();

        wait.until(ExpectedConditions.urlContains("https://app.signnow.com/webapp/documents/"));

        String currentURL = driver.getCurrentUrl();
        assertThat(currentURL)
                .startsWith("https://app.signnow.com")
                .contains("/webapp/documents/");
    }
}

