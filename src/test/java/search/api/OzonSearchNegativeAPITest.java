package search.api;

import by.it_academy.belaya.utils.Steps;
import by.it_academy.belaya.enums.Messages;
import by.it_academy.belaya.utils.ApiResponseParser;
import by.it_academy.belaya.utils.Endpoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class OzonSearchNegativeAPITest {

    private static final Logger logger = LogManager.getLogger();
    private SoftAssertions softly;

    @BeforeAll
    public static void beforeAll() throws IOException {
        Steps.loadHeadersAndCookiesForSearch();
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        logger.info("Starting test: {}", testInfo.getDisplayName());
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("Search by invalid product article")
    public void testSearchByInvalidProductArticle() {
        Response response = Steps.runSearchTest(Endpoints.SEARCH_BY_INVALID_ARTICLE);
        String responseBody = response.body().asString();

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(ApiResponseParser.getTextFromPageNotFoundMessage(responseBody))
                .contains(Messages.PAGE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("Search by symbols")
    public void testSearchBySymbols() {
        Response response = Steps.runSearchTest(Endpoints.SEARCH_BY_SYMBOLS);
        String responseBody = response.body().asString();

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(ApiResponseParser.getTextFromNoResultsMessage(responseBody))
                .contains(Messages.NO_RESULTS.getMessage());
    }

    @Test
    @DisplayName("Search by empty query")
    public void testSearchByEmptyQuery() {
        Response response = Steps.runSearchTest(Endpoints.SEARCH_BY_EMPTY_QUERY);
        String responseBody = response.body().asString();

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(ApiResponseParser.getTextFromErrorHappenedMessage(responseBody))
                .contains(Messages.ERROR_HAPPENED_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Search by spaces")
    public void testSearchBySpaces() {
        Response response = Steps.runSearchTest(Endpoints.SEARCH_BY_SPACES);
        String responseBody = response.body().asString();

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(ApiResponseParser.getTextFromErrorHappenedMessage(responseBody))
                .contains(Messages.ERROR_HAPPENED_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Search by too long query")
    public void testSearchByTooLongQuery() {
        Response response = Steps.runSearchTest(Endpoints.SEARCH_BY_TOO_LONG_QUERY);
        String responseBody = response.body().asString();

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(ApiResponseParser.getTextFromErrorHappenedMessage(responseBody))
                .contains(Messages.ERROR_HAPPENED_MESSAGE.getMessage());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        softly.assertAll();
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}
