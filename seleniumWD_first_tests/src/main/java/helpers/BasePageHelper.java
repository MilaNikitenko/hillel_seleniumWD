package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * BasePageHelper for SignNow
 * Contains general methods to use on different pages
 */
public class BasePageHelper {

    private WebDriver driver;

    /**
     * Constructor for BasePageHelper
     *
     * @param driver WebDriver instance
     */
    public BasePageHelper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Clicks on a web element
     *
     * @param webElement WebElement to be clicked
     */
    public void clickOnWebElement(WebElement webElement) {
        webElement.click();
    }
}

