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

public class TheTwentyFirstCenturySearchNegativeAPITest {

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
    @DisplayName("Verify validation error for empty query")
    public void testValidationErrorForEmptyQuery() {
        String query = "";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Validation error");
        softly.assertThat(response.jsonPath().getString("data.query"))
                .isEqualTo("This value is too short. It should have 1 character or more.");
        softly.assertAll();
    }

    @Test
    @DisplayName("Verify validation error for query with spaces only")
    public void testValidationErrorForQueryWithSpacesOnly() {
        String query = "    ";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Validation error");
        softly.assertThat(response.jsonPath().getString("data.query"))
                .isEqualTo("This value is too short. It should have 1 character or more.");
        softly.assertAll();
    }

    @Test
    @DisplayName("Verify product search with special characters returns no results")
    public void testSearchWithSpecialCharacters() {
        String query = "!@#$%^&*()";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(response.jsonPath().getList("products")).isEmpty();
        softly.assertThat(response.jsonPath().getInt("total")).isEqualTo(0);
        softly.assertAll();
    }

    @Test
    @DisplayName("Verify validation error for empty JSON request")
    public void testValidationErrorForEmptyJsonRequest() {
        String emptyJson = "{}";
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), emptyJson);

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Validation error");
        softly.assertThat(response.jsonPath().getString("data.query"))
                .isEqualTo("Value must be string");
        softly.assertThat(response.jsonPath().getString("data.mode"))
                .isEqualTo("Value must be string");
        softly.assertAll();
    }

    @Test
    @DisplayName("Verify validation error for null query")
    public void testValidationErrorForNullQuery() {
        RequestBodyForSearch requestBody = new RequestBodyForSearch(null);
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Validation error");
        softly.assertThat(response.jsonPath().getString("data.query"))
                .isEqualTo("Value must be string");
        softly.assertAll();
    }

    @Test
    @DisplayName("Verify validation error for excessively long query")
    public void testValidationErrorForExcessivelyLongQuery() {
        String query = "a".repeat(1001);
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Validation error");
        softly.assertThat(response.jsonPath().getString("data.query"))
                .isEqualTo("This value is too long. It should have 255 characters or less.");
        softly.assertAll();
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        softly.assertAll();
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}
