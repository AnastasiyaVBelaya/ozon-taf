package by.it_academy.belaya.utils;

import by.it_academy.belaya.base.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;


public class WaitUtils {

    private static final Logger logger = LogManager.getLogger();

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
}
