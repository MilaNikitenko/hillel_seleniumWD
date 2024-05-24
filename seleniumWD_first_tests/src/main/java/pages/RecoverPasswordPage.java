package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * RecoverPasswordPage class containing locators for elements on the recover password page
 */
public class RecoverPasswordPage {
    private WebDriver driver;

    /**
     * Constructor for RecoverPasswordPage
     */
    public RecoverPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators for elements on the recover password page
    public static final By FORGOT_PASSWORD_BUTTON = By.xpath("//*[@id=\"forgotPswd\"]");
    public static final By FORGOT_EMAIL_FIELD = By.xpath("//*[@data-qa-tag=\"forgot-email\"]");
    public static final By RECOVER_PASSWORD_BUTTON = By.xpath("//*[@aria-label=\"Recover password\"]");
    public static final By SUCCESS_ALERT = By.tagName("h2");
}

