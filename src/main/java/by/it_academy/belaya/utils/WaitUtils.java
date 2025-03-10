package by.it_academy.belaya.utils;

import by.it_academy.belaya.base.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Set;


public class WaitUtils {

    private static final Logger logger = LogManager.getLogger(WaitUtils.class);

    private static final int DEFAULT_TIMEOUT = 10;
    private static final int DEFAULT_POLLING = 500;

    private static FluentWait<WebDriver> createFluentWait() {
        return new FluentWait<>(Singleton.getDriver())
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofMillis(DEFAULT_POLLING))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    public static void waitForElementToBeVisible(WebElement element) {
        try {
            createFluentWait().until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            logger.error("Element not visible after waiting: {}", element, e);
            throw e;
        }
    }

    public static void waitForElementToBeClickable(WebElement element) {
        try {
            createFluentWait().until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            logger.error("Element not clickable after waiting: {}", element, e);
            throw e;
        }
    }

    public static void waitForWindowCountToIncrease(String originalWindowHandle) {
        try {
            createFluentWait().until(driver -> {
                Set<String> windowHandles = driver.getWindowHandles();
                return windowHandles.size() > 1 && !windowHandles.contains(originalWindowHandle);
            });
        } catch (TimeoutException e) {
            logger.error("New window was not opened within the timeout. Original window handle: {}",
                    originalWindowHandle, e);
            throw e;
        }
    }
}
