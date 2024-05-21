package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {

    private static WebDriver driver;

    private WebDriverFactory() {
    }

    public static WebDriver getDriver(String browser) {
        if (driver == null) {
            synchronized (WebDriver.class) {
                if (driver == null){
                    switch (browser.toLowerCase()){
                        case "firefox": driver = new FirefoxDriver();
                        break;
                        case "chrome": driver = new ChromeDriver();
                        break;

                    }
                }
            }
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static void closeDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }
}