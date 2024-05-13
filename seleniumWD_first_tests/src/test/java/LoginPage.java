import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class LoginPage {
    private WebDriver driver;

    private WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver,Duration.ofSeconds(15));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        if (driver!=null)
            driver.quit();
    }

    @BeforeMethod (alwaysRun = true)
    public void openLoginPage(){
        driver.get("https://app.signnow.com/rctapp/login");
    }

    @Test(priority = 1)
    public void checkLoginPageTitle(){
        String actualTitle = driver.getTitle();
        assertEquals("signNow Login", actualTitle);
    }

  @Test(priority = 2)
    public void checkFacebookSocialLoginButton(){
        WebElement facebookButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(" //*[@type= 'button' and @aria-label=\"Log in using Facebook\"]"))));
        assertTrue(facebookButton.isDisplayed());

        facebookButton.click();
        wait.until(ExpectedConditions.urlContains("https://www.facebook.com/"));

        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.startsWith("https://www.facebook.com/"));
    }

    @Test(priority = 3)
    public void checkGoogleSocialLoginButton(){
        WebElement googleButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(@aria-label, \"Log in using Google\")]"))));
        assertTrue(googleButton.isDisplayed());

        googleButton.click();
        wait.until(ExpectedConditions.urlContains("https://accounts.google.com/"));

        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.startsWith("https://accounts.google.com/"));
    }

    @Test(priority = 4)
    public void checkForgotPasswordFunctionality(){
        WebElement forgotPasswordButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"forgotPswd\"]"))));
        assertTrue(forgotPasswordButton.isDisplayed());

        forgotPasswordButton.click();
        wait.until(ExpectedConditions.urlContains("/rctapp/recover-password"));

        WebElement forgotEmailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-qa-tag=\"forgot-email\"]")));
        forgotEmailField.sendKeys("mila.s.nikitenko@gmail.com");

        WebElement recoverPasswordButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-label=\"Recover password\"]")));
        recoverPasswordButton.click();

        WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        String text = successAlert.getText();
        assertEquals("Great job! Just check your Inbox.", text);
    }

    @Test(priority = 5)
    public void checkRegistrationPageOpening(){
        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(text(), \"Sign up for signNow for free\")]"))));
        assertTrue(signUpButton.isDisplayed());
        signUpButton.click();

        Set <String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            driver.switchTo().window(handle);
        }

        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.contains("/purchase/business_free_trial/loggedout"));
    }

    @Test(priority = 6)
    public void checkSuccessLogin(){
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id, \"login\")]")));
        emailField.sendKeys("mila.s.nikitenko@gmail.com");

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pswd\"]")));
        passwordField.sendKeys("123456");

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Log in\"]")));
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click();", loginButton);

        wait.until(ExpectedConditions.urlContains("https://app.signnow.com/webapp/documents/"));

        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.contains("https://app.signnow.com/webapp/documents/"));
    }
}

