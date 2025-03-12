package search.api;

import by.it_academy.belaya.enums.Messages;
import by.it_academy.belaya.utils.ApiResponseParser;
import by.it_academy.belaya.utils.BrowserConfigLoader;
import by.it_academy.belaya.utils.Endpoints;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


public class OzonSearchNegativeAPITest {

    private static Map<String, String> headers;
    private static Map<String, String> cookies;
    private static final Logger logger = LogManager.getLogger();

    @BeforeAll
    public static void beforeAll() throws IOException {
        headers = BrowserConfigLoader.loadHeaders();
        cookies = BrowserConfigLoader.loadCookies();
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Search by invalid product article")
    public void testSearchByInvalidProductArticle() {
        String expected = Messages.PAGE_NOT_FOUND.getMessage();
        String responseBody = given()
                .headers(headers)
                .cookies(cookies)
                .when()
                .get(Endpoints.getSearchUrl(Endpoints.SEARCH_BY_INVALID_ARTICLE))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        String actualText = ApiResponseParser.getTextFromPageNotFoundMessage(responseBody);

        assertThat(actualText, containsString(expected));
    }

    @Test
    @DisplayName("Search by symbols")
    public void testSearchBySymbols() {
        String expected = Messages.NO_RESULTS.getMessage();
        String responseBody = given()
                .headers(headers)
                .cookies(cookies)
                .when()
                .get(Endpoints.getSearchUrl(Endpoints.SEARCH_BY_SYMBOLS))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        String actualText = ApiResponseParser.getTextFromNoResultsMessage(responseBody);

        assertThat(actualText, containsString(expected));
    }

    @Test
    @DisplayName("Search by empty query")
    public void testSearchByEmptyQuery() {
        String expected = Messages.ERROR_HAPPENED_MESSAGE.getMessage();
        String responseBody = given()
                .headers(headers)
                .cookies(cookies)
                .when()
                .get(Endpoints.getSearchUrl(Endpoints.SEARCH_BY_EMPTY_QUERY))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        String actualText = ApiResponseParser.getTextFromErrorHappenedMessage(responseBody);

        assertThat(actualText, containsString(expected));
    }

    @Test
    @DisplayName("Search by spaces")
    public void testSearchBySpaces() {
        String expected = Messages.ERROR_HAPPENED_MESSAGE.getMessage();
        String responseBody = given()
                .headers(headers)
                .cookies(cookies)
                .when()
                .get(Endpoints.getSearchUrl(Endpoints.SEARCH_BY_SPACES))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        String actualText = ApiResponseParser.getTextFromErrorHappenedMessage(responseBody);

        assertThat(actualText, containsString(expected));
    }

    @Test
    @DisplayName("Search by too long query")
    public void testSearchByTooLongQuery() {
        String expected = Messages.ERROR_HAPPENED_MESSAGE.getMessage();
        String responseBody = given()
                .headers(headers)
                .cookies(cookies)
                .when()
                .get(Endpoints.getSearchUrl(Endpoints.SEARCH_BY_TOO_LONG_QUERY))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        String actualText = ApiResponseParser.getTextFromErrorHappenedMessage(responseBody);

        assertThat(actualText, containsString(expected));
    }

    @AfterEach
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}


