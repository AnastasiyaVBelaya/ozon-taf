package search.api;

import by.it_academy.belaya.testdata.RequestBodyForSearch;
import by.it_academy.belaya.utils.Endpoints;
import by.it_academy.belaya.utils.Steps;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.io.IOException;


public class TheTwentyFirstCenturySearchPositiveAPITest {

    private static final Logger logger = LogManager.getLogger();
    private SoftAssertions softly;

    @BeforeAll
    public static void beforeAll() throws IOException {
        Steps.loadHeaders();
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        logger.info("Starting test: {}", testInfo.getDisplayName());
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("Verify product search includes query in product name")
    public void testSearchResultsContainQueryInProductName() {
        String query = "телевизор";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(response.jsonPath().getList("products.name").stream()
                .anyMatch(name -> name.toString().toLowerCase().contains(query.toLowerCase()))).isTrue();
        softly.assertAll();
    }

    @Test
    @DisplayName("Search with exact match in product name")
    public void testExactMatchInProductName() {
        String query = "телевизор Samsung";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(response.jsonPath().getList("products.name").stream()
                .anyMatch(name -> name.toString().toLowerCase().contains(query.toLowerCase()))).isTrue();
        softly.assertAll();
    }

    @Test
    @DisplayName("Verify product search with uppercase query")
    public void testSearchWithUppercaseQuery() {
        String query = "ТЕЛЕВИЗОР";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(response.jsonPath().getList("products.name").stream()
                .anyMatch(name -> name.toString().toLowerCase().contains(query.toLowerCase()))).isTrue();
        softly.assertAll();
    }

    @Test
    @DisplayName("Verify product search includes partial match in product name")
    public void testSearchWithPartialProductName() {
        String query = "теле";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(response.jsonPath().getList("products.name").stream()
                .anyMatch(name -> name.toString().toLowerCase().contains(query.toLowerCase()))).isTrue();
        softly.assertAll();
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        softly.assertAll();
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}
