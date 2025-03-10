package by.it_academy.belaya.pages;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {

    private static final String URL = "https://ozon.by/";
    private static final Logger logger = LogManager.getLogger();

    @FindBy(xpath = "//input[@name='text']")
    private WebElement searchInputField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchSubmitButton;

    @FindBy(xpath = "//div[@class='q4b011-a c5t_49_11']")
    private WebElement loginButton;

    public HomePage() {
        WebDriver driver = Singleton.getDriver();
        PageFactory.initElements(driver, this);
        driver.get(URL);
        logger.info("Opened Home Page: {}", URL);
    }

    public WebElement getSearchInputField() {
        WaitUtils.waitForElementToBeVisible(searchInputField);
        return searchInputField;
    }

    public HomePage enterSearchQuery(String text) {
        WaitUtils.waitForElementToBeVisible(searchInputField);
        searchInputField.sendKeys(text);
        logger.info("Entered search query: {}", text);
        return this;
    }

    public SearchResultPage submitSearchByButton() {
        searchSubmitButton.click();
        logger.info("Search submitted by search button");
        return new SearchResultPage();
    }

    public SearchResultPage submitSearchByEnter() {
        searchInputField.sendKeys(Keys.ENTER);
        logger.info("Search submitted by Enter key");
        return new SearchResultPage();
    }

    public LoginPage openLoginPage() {
        WaitUtils.waitForElementToBeClickable(loginButton);
        loginButton.click();
        return new LoginPage();
    }
}
