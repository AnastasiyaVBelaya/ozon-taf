package search.api;

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


public class OzonSearchPositiveAPITest {

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
    @DisplayName("Search by valid product article - test article button")
    public void testSearchByValidProductArticle() {
        String expected = Endpoints.VALID_ARTICLE;
        String responseBody = given()
                .headers(headers)
                .cookies(cookies)
                .when()
                .get(Endpoints.getSearchUrl(Endpoints.SEARCH_BY_VALID_ARTICLE))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        String actualText = ApiResponseParser.getArticleFromResponse(responseBody);

        assertThat(actualText, containsString(expected));
    }

    @Test
    @DisplayName("Search by valid product name - test result header")
    public void testSearchByValidProductNameHeader() {
        String expected = Endpoints.VALID_NAME_FOR_HEADER;
        String responseBody = given()
                .headers(headers)
                .cookies(cookies)
                .when()
                .get(Endpoints.getSearchUrl(Endpoints.SEARCH_BY_VALID_NAME_FOR_HEADER))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        String actualText = ApiResponseParser.getTextFromResultHeader(responseBody);

        assertThat(actualText, containsString(expected));
    }

    @Test
    @DisplayName("Search by valid product name - test first product card")
    public void testSearchByValidProductNameFirstProductCard() {
        String expected = Endpoints.VALID_NAME_FOR_CARD.toLowerCase();
        String responseBody = given()
                .headers(headers)
                .cookies(cookies)
                .when()
                .get(Endpoints.getSearchUrl(Endpoints.SEARCH_BY_VALID_NAME_FOR_CARD))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        String actualText = ApiResponseParser.getTextFromFirstProductCard(responseBody).toLowerCase();

        assertThat(actualText, containsString(expected));
    }

    @AfterEach
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}


