/**
 * Provides classes and methods for testing the login functionality.
 * Includes tests for user login, social login, and password recovery.
 *  @author Liudmyla Nikitenko
 */

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.RecoverPasswordPage;
import utils.PropertyFactory;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static pages.LoginPage.*;
import static pages.RecoverPasswordPage.*;
import dataproviders.LoginPageDataProviders;


@Owner("Liudmyla Nikitenko")
@Epic("My first UI tests")
@Link(name = "Login_Page", url = "https://app.signnow.com/rctapp/login")

public class AuthorizationTests extends BaseTest {


    @Flaky
    @Severity(SeverityLevel.NORMAL)
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

    @Issue("Task # ")
    @Severity(SeverityLevel.CRITICAL)
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

    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void checkGoogleSocialLoginButton(){
        WebElement googleButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(GOOGLE_BUTTON)));
        assertTrue(googleButton.isDisplayed());

        loginPageHelper.clickOnWebElement(googleButton);
        wait.until(ExpectedConditions.urlContains("https://accounts.google.com/"));

        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.startsWith("https://accounts.google.com/"));
    }

    @Severity(SeverityLevel.CRITICAL)
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

    @Severity(SeverityLevel.BLOCKER)
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

    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 6, dataProviderClass = LoginPageDataProviders.class, dataProvider = "loginData")
    public void tryLoginWithoutPassword(String email){

        loginPageHelper.fillEmailOnLoginPage(email);
        loginPageHelper.clickOnLoginButton();

        WebElement passwordErrorAlertText = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_ERROR_ALERT));
        String actualErrorText = passwordErrorAlertText.getText();
        assertThat(actualErrorText).
                isEqualTo("Password is required");

        String currentURL = driver.getCurrentUrl();
        assertThat(currentURL).isEqualTo(PropertyFactory.getLoginPageLinkProperty());
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 7)
    public void checkSuccessLogin(){
        String email = testConfig.getProperty("user.email");
        String password = testConfig.getProperty("user.password");

        loginPageHelper.fillEmailOnLoginPage(email);
        loginPageHelper.fillPasswordOnLoginPage(PASSWORD_FIELD);
        loginPageHelper.clickOnLoginButton();

        wait.until(ExpectedConditions.urlContains("https://app.signnow.com/webapp/documents/"));

        String currentURL = driver.getCurrentUrl();
        assertThat(currentURL)
                .startsWith("https://app.signnow.com")
                .contains("/webapp/documents/");
    }



}

