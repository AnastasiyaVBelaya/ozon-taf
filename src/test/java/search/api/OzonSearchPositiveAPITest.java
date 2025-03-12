package search.api;

import by.it_academy.belaya.utils.Steps;
import by.it_academy.belaya.utils.ApiResponseParser;
import by.it_academy.belaya.utils.Endpoints;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.IOException;


public class OzonSearchPositiveAPITest {

    private static final Logger logger = LogManager.getLogger();

    @BeforeAll
    public static void beforeAll() throws IOException {
        Steps.loadHeadersAndCookies();
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Search by valid product article - test article button")
    public void testSearchByValidProductArticle() {
        Steps.runSearchTest(Endpoints.SEARCH_BY_VALID_ARTICLE,
                Endpoints.VALID_ARTICLE,
                ApiResponseParser::getArticleFromResponse);
    }

    @Test
    @DisplayName("Search by valid product name - test result header")
    public void testSearchByValidProductNameHeader() {
        Steps.runSearchTest(Endpoints.SEARCH_BY_VALID_NAME_FOR_HEADER,
                Endpoints.VALID_NAME_FOR_HEADER,
                ApiResponseParser::getTextFromResultHeader);
    }

    @Test
    @DisplayName("Search by valid product name - test first product card")
    public void testSearchByValidProductNameFirstProductCard() {
        Steps.runSearchTest(Endpoints.SEARCH_BY_VALID_NAME_FOR_CARD,
                Endpoints.VALID_NAME_FOR_CARD.toLowerCase(),
                response -> ApiResponseParser.getTextFromFirstProductCard(response).toLowerCase());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}


