package search.ui;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.pages.HomePage;
import by.it_academy.belaya.testdata.ArticleNumber;
import by.it_academy.belaya.testdata.PartialProductName;
import by.it_academy.belaya.testdata.ProductName;
import org.junit.jupiter.api.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class OzonSearchBarPositiveUITest {

    private HomePage homePage;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        homePage = new HomePage();
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Check search bar enabled")
    public void testSearchBarEnabled() {
        Assertions.assertTrue(homePage
                .getSearchInputField()
                .isEnabled());
    }

    @Test
    @DisplayName("Search product by valid article (by button)")
    public void testSearchProductByValidArticleByButton() {
        String searchQuery = new ArticleNumber().getRandomValue();
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getValidArticleSearchResult();

        Assertions.assertTrue(result.contains(searchQuery));
    }

    @Test
    @DisplayName("Search product by valid article (by Enter)")
    public void testSearchProductByValidArticleByEnter() {
        String searchQuery = new ArticleNumber().getRandomValue();
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByEnter()
                .getValidArticleSearchResult();

        Assertions.assertTrue(result.contains(searchQuery));
    }

    @Test
    @DisplayName("Search by product name")
    public void testSearchByProductName() {
        String searchQuery = new ProductName().getRandomValue();
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getSearchResultTitle();

        Assertions.assertTrue(result.toLowerCase().contains(searchQuery.toLowerCase()));
    }

    @Test
    @DisplayName("Search with partial product name")
    public void testSearchWithPartialProductName() {
        String searchQuery = new PartialProductName().getRandomValue();
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getSearchResultTitle();

        Assertions.assertTrue(result.toLowerCase().contains(searchQuery.toLowerCase()));
    }

    @Test
    @DisplayName("Search result message contains product name")
    public void testSearchResultMessageContainsProductName() {
        String searchQuery = new ProductName().getRandomValue();
        String resultMessage = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getSearchResultMessage();

        Assertions.assertTrue(resultMessage.contains(searchQuery));
    }

    @AfterEach
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
        Singleton.quit();
    }
}
