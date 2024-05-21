package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage class containing locators for elements on the login page
 */
public class LoginPage {
    private WebDriver driver;

    /**
     * Constructor for LoginPage
     *
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators for elements on the login page
    public static final String FACEBOOK_BUTTON = " //*[@type= 'button' and @aria-label=\"Log in using Facebook\"]";
    public static final By GOOGLE_BUTTON = By.xpath("//button[contains(@aria-label, \"Log in using Google\")]");
    public static final By SIGNUP_BUTTON = By.xpath("//a[contains(text(), \"Sign up for signNow for free\")]");
    public static final By EMAIL_FIELD = By.xpath("//input[contains(@id, \"login\")]");
    public static final By PASSWORD_FIELD = By.xpath("//*[@id=\"pswd\"]");
    public static final By LOGIN_BUTTON = By.xpath("//button[@aria-label=\"Log in\"]");
}
