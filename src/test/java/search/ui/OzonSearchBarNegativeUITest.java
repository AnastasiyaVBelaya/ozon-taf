package search.ui;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.enums.Messages;
import by.it_academy.belaya.pages.HomePage;
import by.it_academy.belaya.testdata.*;
import by.it_academy.belaya.utils.TestDataUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

public class OzonSearchBarNegativeUITest {

    private HomePage homePage;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        homePage = new HomePage();
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Search with empty query")
    public void testSearchWithEmptyQuery() {
        String expectedUrl = Singleton.getDriver().getCurrentUrl();
        homePage.enterSearchQuery("").submitSearchByButton();
        String currentUrl = Singleton.getDriver().getCurrentUrl();

        Assertions.assertTrue(currentUrl.contains(expectedUrl));
    }

    @Test
    @DisplayName("Search with single space")
    public void testSearchWithSingleSpace() {
        String expectedUrl = Singleton.getDriver().getCurrentUrl();
        homePage.enterSearchQuery(" ").submitSearchByButton();
        String currentUrl = Singleton.getDriver().getCurrentUrl();

        Assertions.assertTrue(currentUrl.contains(expectedUrl));
    }

    @Test
    @DisplayName("Search with single character")
    public void testSearchWithSingleCharacter() {
        String expectedUrl = Singleton.getDriver().getCurrentUrl();
        homePage.enterSearchQuery("a").submitSearchByButton();
        String currentUrl = Singleton.getDriver().getCurrentUrl();

        Assertions.assertTrue(currentUrl.contains(expectedUrl));
    }

    @Test
    @DisplayName("Search product by symbols")
    public void testSearchProductBySymbols() {
        String searchQuery = new Symbol().getRandomValue();
        String expectedResult = Messages.NO_RESULTS.getMessage();
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getNoResultsMessage();

        Assertions.assertTrue(result.contains(expectedResult));
    }

    @Test
    @DisplayName("Search product by correct, but non-existent article")
    public void testSearchProductByNonExistentArticle() {
        String searchQuery = new NonExistentArticle().getRandomValue();
        String expectedResult = Messages.PAGE_NOT_FOUND.getMessage();
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getPageNotFoundMessage();

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Search product with maximum query length")
    public void testSearchProductWithMaxQueryLength() {
        String searchQuery = "9".repeat(255);
        String expectedResult = Messages.NO_RESULTS.getMessage();
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getNoResultsMessage();

        Assertions.assertTrue(result.contains(expectedResult));
    }

    @Test
    @DisplayName("Search product with query exceeding max length")
    public void testSearchProductWithExceedingQueryLength() {
        String searchQuery = "9".repeat(1000);
        String expectedResult = Messages.NO_RESULTS.getMessage();
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getNoResultsMessage();

        Assertions.assertTrue(result.contains(expectedResult));
    }

    @Test
    @DisplayName("Search query by article with leading and trailing spaces")
    public void testSearchQueryByArticleWithLeadingAndTrailingSpaces() {
        String article = new ArticleNumber().getRandomValue();
        String searchQuery = TestDataUtils
                .generateSearchQueryWithRandomSpaces(article);
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getValidArticleSearchResult();

        Assertions.assertEquals("Артикул: " + article, result);
    }

    @Test
    @DisplayName("Search query by product name with leading and trailing spaces")
    public void testSearchQueryByProductNameWithLeadingAndTrailingSpaces() {
        String productName = new ProductName().getRandomValue();
        String searchQuery = TestDataUtils.generateSearchQueryWithRandomSpaces(productName);
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getSearchResultTitle();

        Assertions.assertTrue(result.toLowerCase().contains(productName.toLowerCase()));
    }

    @Test
    @DisplayName("Search with spaces in the middle of the query")
    public void testSearchQueryWithSpacesInTheMiddle() {
        String searchQuery = new QueryWithSpacesInTheMiddle().getRandomValue();
        String expectedResult = Messages.INVALID_SEARCH_QUERY.getMessage();
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getNoExactResultsMessage();

        Assertions.assertTrue(result.contains(expectedResult));
    }

    @Test
    @DisplayName("Correct query after incorrect layout")
    public void testCorrectSearchQuery() {
        String searchQuery = "ntktdbpjh";
        String expectedResult = "телевизор";
        String correctedQuery = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getSearchQueryAfterSearch();

        Assertions.assertEquals(expectedResult, correctedQuery);
    }

    @AfterEach
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
        Singleton.quit();
    }
}
