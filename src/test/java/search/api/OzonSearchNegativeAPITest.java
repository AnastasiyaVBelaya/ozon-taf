package search.api;

import by.it_academy.belaya.utils.Steps;
import by.it_academy.belaya.enums.Messages;
import by.it_academy.belaya.utils.ApiResponseParser;
import by.it_academy.belaya.utils.Endpoints;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.IOException;


public class OzonSearchNegativeAPITest {

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
    @DisplayName("Search by invalid product article")
    public void testSearchByInvalidProductArticle() {
        Steps.runSearchTest(Endpoints.SEARCH_BY_INVALID_ARTICLE,
                Messages.PAGE_NOT_FOUND.getMessage(),
                ApiResponseParser::getTextFromPageNotFoundMessage);
    }

    @Test
    @DisplayName("Search by symbols")
    public void testSearchBySymbols() {
        Steps.runSearchTest(Endpoints.SEARCH_BY_SYMBOLS,
                Messages.NO_RESULTS.getMessage(),
                ApiResponseParser::getTextFromNoResultsMessage);
    }

    @Test
    @DisplayName("Search by empty query")
    public void testSearchByEmptyQuery() {
        Steps.runSearchTest(Endpoints.SEARCH_BY_EMPTY_QUERY,
                Messages.ERROR_HAPPENED_MESSAGE.getMessage(),
                ApiResponseParser::getTextFromErrorHappenedMessage);
    }

    @Test
    @DisplayName("Search by spaces")
    public void testSearchBySpaces() {
        Steps.runSearchTest(Endpoints.SEARCH_BY_SPACES,
                Messages.ERROR_HAPPENED_MESSAGE.getMessage(),
                ApiResponseParser::getTextFromErrorHappenedMessage);
    }

    @Test
    @DisplayName("Search by too long query")
    public void testSearchByTooLongQuery() {
        Steps.runSearchTest(Endpoints.SEARCH_BY_TOO_LONG_QUERY,
                Messages.ERROR_HAPPENED_MESSAGE.getMessage(),
                ApiResponseParser::getTextFromErrorHappenedMessage);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}


