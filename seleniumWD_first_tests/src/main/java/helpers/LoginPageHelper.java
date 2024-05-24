package helpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropertyFactory;
import java.time.Duration;
import static pages.LoginPage.*;

/**
 * LoginPageHelper for the login page "https://app.signnow.com/rctapp/login"
 */
public class LoginPageHelper extends BasePageHelper {
    protected WebDriver driver;

    public LoginPageHelper(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    /**
     * Fills the email field on the login page
     */
    public void fillEmailOnLoginPage(String email) {
        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD));
        action.moveToElement(emailField).sendKeys(PropertyFactory.getUserEmailProperty()).build().perform();
    }

    /**
     * Fills the password field on the login page
     */
    public void fillPasswordOnLoginPage(By passwordField) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordElement.sendKeys(PropertyFactory.getUserPasswordProperty());
    }

    /**
     * Clicks on the login button
     */
    public void clickOnLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click();", loginButton);
    }
}
