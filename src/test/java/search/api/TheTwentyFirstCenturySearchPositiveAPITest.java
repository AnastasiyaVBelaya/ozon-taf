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
@Feature("Positive Search Scenarios")
@ExtendWith(AllureJunit5.class)
public class TheTwentyFirstCenturySearchPositiveAPITest {

    private static final Logger logger = LogManager.getLogger();
    private SoftAssertions softly;

    @BeforeAll
    @Step("Загрузка заголовков перед выполнением всех тестов")
    public static void beforeAll() throws IOException {
        Steps.loadHeaders();
    }

    @BeforeEach
    @Step("Подготовка к выполнению теста")
    public void beforeEach(TestInfo testInfo) {
        logger.info("Starting test: {}", testInfo.getDisplayName());
        softly = new SoftAssertions();
    }

    @Test
    @Description("Проверка, что результаты поиска содержат запрос в названии продукта")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Verify product search includes query in product name")
    public void testSearchResultsContainQueryInProductName() {
        Allure.step("Генерация запроса поиска: телевизор");
        String query = "телевизор";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);

        Allure.step("Отправка HTTP-запроса на поиск");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        Allure.step("Проверка, что статус ответа равен 200");
        softly.assertThat(response.statusCode()).isEqualTo(200);

        Allure.step("Проверка, что результаты поиска содержат запрос");
        softly.assertThat(response.jsonPath().getList("products.name").stream()
                .anyMatch(name -> name.toString().toLowerCase().contains(query.toLowerCase()))).isTrue();

        softly.assertAll();
    }

    @Test
    @Description("Проверка точного соответствия запроса в названии продукта")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Search with exact match in product name")
    public void testExactMatchInProductName() {
        Allure.step("Генерация запроса поиска: телевизор Samsung");
        String query = "телевизор Samsung";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);

        Allure.step("Отправка HTTP-запроса на поиск");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        Allure.step("Проверка, что статус ответа равен 200");
        softly.assertThat(response.statusCode()).isEqualTo(200);

        Allure.step("Проверка, что результаты поиска содержат запрос");
        softly.assertThat(response.jsonPath().getList("products.name").stream()
                .anyMatch(name -> name.toString().toLowerCase().contains(query.toLowerCase()))).isTrue();

        softly.assertAll();
    }

    @Test
    @Description("Проверка результатов поиска с запросом в верхнем регистре")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Verify product search with uppercase query")
    public void testSearchWithUppercaseQuery() {
        Allure.step("Генерация запроса поиска: ТЕЛЕВИЗОР");
        String query = "ТЕЛЕВИЗОР";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);

        Allure.step("Отправка HTTP-запроса на поиск");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        Allure.step("Проверка, что статус ответа равен 200");
        softly.assertThat(response.statusCode()).isEqualTo(200);

        Allure.step("Проверка, что результаты поиска содержат запрос");
        softly.assertThat(response.jsonPath().getList("products.name").stream()
                .anyMatch(name -> name.toString().toLowerCase().contains(query.toLowerCase()))).isTrue();

        softly.assertAll();
    }

    @Test
    @Description("Проверка результатов поиска с частичным совпадением запроса")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Verify product search includes partial match in product name")
    public void testSearchWithPartialProductName() {
        Allure.step("Генерация запроса поиска: теле");
        String query = "теле";
        RequestBodyForSearch requestBody = new RequestBodyForSearch(query);

        Allure.step("Отправка HTTP-запроса на поиск");
        Response response = Steps.runSearchTest(Endpoints.getSearchUrl(), requestBody.getBody());

        Allure.step("Проверка, что статус ответа равен 200");
        softly.assertThat(response.statusCode()).isEqualTo(200);

        Allure.step("Проверка, что результаты поиска содержат запрос");
        softly.assertThat(response.jsonPath().getList("products.name").stream()
                .anyMatch(name -> name.toString().toLowerCase().contains(query.toLowerCase()))).isTrue();

        softly.assertAll();
    }

    @AfterEach
    @Step("Завершение теста")
    public void tearDown(TestInfo testInfo) {
        softly.assertAll();
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}
