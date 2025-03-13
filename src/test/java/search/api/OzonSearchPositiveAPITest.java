package search.api;

import by.it_academy.belaya.utils.Steps;
import by.it_academy.belaya.utils.ApiResponseParser;
import by.it_academy.belaya.utils.Endpoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class OzonSearchPositiveAPITest {

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
    @DisplayName("Search by valid product article - test article button")
    public void testSearchByValidProductArticle() {
        Response response = Steps.runSearchTest(Endpoints.SEARCH_BY_VALID_ARTICLE);
        String responseBody = response.body().asString();

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(ApiResponseParser.getArticleFromResponse(responseBody))
                .contains(Endpoints.VALID_ARTICLE);
    }

    @Test
    @DisplayName("Search by valid product name - test result header")
    public void testSearchByValidProductNameHeader() {
        Response response = Steps.runSearchTest(Endpoints.SEARCH_BY_VALID_NAME_FOR_HEADER);
        String responseBody = response.body().asString();

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(ApiResponseParser.getTextFromResultHeader(responseBody))
                .containsIgnoringCase(Endpoints.VALID_NAME_FOR_HEADER);
    }

    @Test
    @DisplayName("Search by valid product name - test first product card")
    public void testSearchByValidProductNameFirstProductCard() {
        Response response = Steps.runSearchTest(Endpoints.SEARCH_BY_VALID_NAME_FOR_CARD);
        String responseBody = response.body().asString();

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(ApiResponseParser.getTextFromFirstProductCard(responseBody))
                .containsIgnoringCase(Endpoints.VALID_NAME_FOR_CARD);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        softly.assertAll();
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}
