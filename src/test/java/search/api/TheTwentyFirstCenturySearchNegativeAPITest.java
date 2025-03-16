package search.api;

import by.it_academy.belaya.testdata.RequestBodyForSearch;
import by.it_academy.belaya.utils.Endpoints;
import by.it_academy.belaya.utils.Steps;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

@Epic("Search API Tests")
@Feature("Negative Search Scenarios")
@ExtendWith(AllureJunit5.class)
public class TheTwentyFirstCenturySearchNegativeAPITest {

    private static final Logger logger = LogManager.getLogger();
    private SoftAssertions softly;

    @BeforeAll
    @Step("Загрузка заголовков перед началом всех тестов")
    public static void beforeAll() throws IOException {
        Steps.loadHeaders();
    }

    @BeforeEach
    @Step("Подготовка к тесту")
    public void beforeEach(TestInfo testInfo) {
        logger.info("Starting test: {}", testInfo.getDisplayName());
        softly = new SoftAssertions();
    }

    @Test
    @Description("Проверка валидационной ошибки для пустого запроса")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Verify validation error for empty query")
    public void testValidationErrorForEmptyQuery() {
        Allure.step("Генерация пустого запроса");
        String query = "";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);

        Allure.step("Отправка запроса поиска с пустым значением");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        Allure.step("Проверка кода статуса ответа и сообщения об ошибке");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Validation error");
        softly.assertThat(response.jsonPath().getString("data.query"))
                .isEqualTo("This value is too short. It should have 1 character or more.");
        softly.assertAll();
    }

    @Test
    @Description("Проверка валидационной ошибки для запроса, содержащего только пробелы")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Verify validation error for query with spaces only")
    public void testValidationErrorForQueryWithSpacesOnly() {
        Allure.step("Генерация строки, содержащей только пробелы");
        String query = "    ";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);

        Allure.step("Отправка запроса поиска");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        Allure.step("Проверка кода статуса ответа и сообщения об ошибке");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Validation error");
        softly.assertThat(response.jsonPath().getString("data.query"))
                .isEqualTo("This value is too short. It should have 1 character or more.");
        softly.assertAll();
    }

    @Test
    @Description("Проверка поиска по специальным символам, которые возвращают пустые результаты")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Verify product search with special characters returns no results")
    public void testSearchWithSpecialCharacters() {
        Allure.step("Генерация строки, содержащей специальные символы");
        String query = "!@#$%^&*()";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);

        Allure.step("Отправка запроса поиска");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        Allure.step("Проверка, что результаты поиска пусты");
        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(response.jsonPath().getList("products")).isEmpty();
        softly.assertThat(response.jsonPath().getInt("total")).isEqualTo(0);
        softly.assertAll();
    }

    @Test
    @Description("Проверка ошибки валидации для пустого JSON-запроса")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Verify validation error for empty JSON request")
    public void testValidationErrorForEmptyJsonRequest() {
        Allure.step("Создание пустого JSON-запроса");
        String emptyJson = "{}";

        Allure.step("Отправка пустого JSON-запроса");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), emptyJson);

        Allure.step("Проверка кода статуса ответа и сообщения об ошибке");
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
    @Description("Проверка ошибки валидации для null в запросе")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Verify validation error for null query")
    public void testValidationErrorForNullQuery() {
        Allure.step("Создание null-запроса");
        RequestBodyForSearch requestBody = new RequestBodyForSearch(null);

        Allure.step("Отправка null-запроса");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        Allure.step("Проверка кода статуса ответа и сообщения об ошибке");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Validation error");
        softly.assertThat(response.jsonPath().getString("data.query"))
                .isEqualTo("Value must be string");
        softly.assertAll();
    }

    @Test
    @Description("Проверка ошибки валидации для слишком длинного запроса")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Verify validation error for excessively long query")
    public void testValidationErrorForExcessivelyLongQuery() {
        Allure.step("Генерация запроса длиной более 1000 символов");
        String query = "a".repeat(1001);
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);

        Allure.step("Отправка слишком длинного запроса");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        Allure.step("Проверка кода статуса ответа и сообщения об ошибке");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Validation error");
        softly.assertThat(response.jsonPath().getString("data.query"))
                .isEqualTo("This value is too long. It should have 255 characters or less.");
        softly.assertAll();
    }

    @AfterEach
    @Step("Завершение теста")
    public void tearDown(TestInfo testInfo) {
        softly.assertAll();
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}
