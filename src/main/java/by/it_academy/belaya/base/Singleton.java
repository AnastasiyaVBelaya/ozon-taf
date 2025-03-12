package by.it_academy.belaya.base;

import by.it_academy.belaya.exceptions.WebDriverCreationException;
import by.it_academy.belaya.utils.BrowserConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Singleton {

    private static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(Singleton.class);

    private Singleton() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
            logger.info("Browser started: Chrome");
        }
        return driver;
    }

    private static WebDriver createDriver() {
        try {
            ChromeOptions options = BrowserConfigLoader.loadChromeOptions();
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            return driver;
        } catch (WebDriverException e) {
            logger.error("Error occurred while creating the driver", e);
            throw new WebDriverCreationException(e);
        }
    }

    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("Browser closed");
        }
    }
}
