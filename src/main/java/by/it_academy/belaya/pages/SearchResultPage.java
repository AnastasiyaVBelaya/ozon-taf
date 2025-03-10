package by.it_academy.belaya.pages;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;

public class SearchResultPage {

    private static final Logger logger = LogManager.getLogger();

    @FindBy(xpath = "//input[@name='text']")
    private WebElement searchInputField;

    @FindBy(xpath = "//button[@data-widget='webDetailSKU']")
    private WebElement validArticleNumberResult;

    @FindBy(xpath = "//h2[@class='c4']")
    private WebElement pageNotFoundMessage;

    @FindBy(xpath = "//div[@data-widget='searchResultsError']")
    private WebElement noResultsMessage;

    @FindBy(xpath = "//div[@data-widget='fulltextResultsHeader']")
    private WebElement noExactResultsMessage;

    @FindBy(xpath = "(//a[@class='q4b011-a tile-clickable-element lj9_25'])[1]")
    private WebElement searchResultTitle;

    @FindBy(xpath = "//div[@data-widget='fulltextResultsHeader']")
    private WebElement searchResultMessage;

    public SearchResultPage() {
        WebDriver driver = Singleton.getDriver();
        PageFactory.initElements(driver, this);
        logger.info("Opened SearchResult Page: {}",Singleton.getDriver().getCurrentUrl());
    }

    public String getValidArticleSearchResult() {
        String result = getTextWhenVisible(validArticleNumberResult);
        logger.info("Valid article number result: {}", result);
        return result;
    }

    public String getPageNotFoundMessage() {
        String message = getTextWhenVisible(pageNotFoundMessage);
        logger.info("Page not found message: {}", message);
        return message;
    }

    public String getNoResultsMessage() {
        String message = getTextWhenVisible(noResultsMessage);
        logger.info("No results message: {}", message);
        return message;
    }

    public String getNoExactResultsMessage() {
        String message = getTextWhenVisible(noExactResultsMessage);
        logger.info("No exact results message: {}", message);
        return message;
    }

    public String getSearchResultTitle() {
        String title = getTextWhenVisible(searchResultTitle);
        logger.info("Search result title: {}", title);
        return title;
    }

    public String getSearchResultMessage() {
        String message = getTextWhenVisible(searchResultMessage);
        logger.info("Search result message: {}", message);
        return message;
    }

    public String getSearchQueryAfterSearch() {
        String query = getElementDomProperty(searchInputField);
        logger.info("Search query after search: {}", query);
        return query;
    }

    public boolean doesResultContainsAllSearchWords(String searchQuery) {
        String result = getSearchResultTitle().toLowerCase();
        boolean containsAll = Arrays.stream(searchQuery.split(" "))
                .allMatch(word -> result.contains(word.toLowerCase()));
        logger.info("Does the result contain all search words ('{}')? {}", searchQuery, containsAll);
        return containsAll;
    }

    private String getTextWhenVisible(WebElement element) {
        WaitUtils.waitForElementToBeVisible(element);
        return element.getText().trim();
    }

    private String getElementDomProperty(WebElement element) {
        WaitUtils.waitForElementToBeVisible(element);
        return element.getDomProperty("value");
    }
}
