import helpers.BasePageHelper;
import helpers.LoginPageHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.RecoverPasswordPage;
import utils.WebDriverFactory;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected LoginPage loginPage;
    protected RecoverPasswordPage recoverPasswordPage;
    protected LoginPageHelper loginPageHelper;
    protected BasePageHelper basePageHelper;

    @Parameters("browser")

    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser){
        // Initialize WebDriver
        driver = WebDriverFactory.getDriver(browser);

        // Initialize WebDriverWait
        Duration timeout = Duration.ofSeconds(15);
        driver.manage().timeouts().implicitlyWait(timeout);
        wait = new WebDriverWait(driver, timeout);

        // Initialize page objects
        loginPage = new LoginPage(driver);
        recoverPasswordPage = new RecoverPasswordPage(driver);
        loginPageHelper = new LoginPageHelper(driver);
        basePageHelper = new BasePageHelper(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        WebDriverFactory.closeDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public void openLoginPage(){
        driver.get("https://app.signnow.com/rctapp/login");
    }
}
