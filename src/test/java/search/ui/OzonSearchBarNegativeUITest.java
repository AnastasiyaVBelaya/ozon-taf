package search.ui;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.enums.Messages;
import by.it_academy.belaya.pages.HomePage;
import by.it_academy.belaya.testdata.*;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("Ozon UI Tests")
@Feature("Negative Search Bar Scenarios")
@ExtendWith(AllureJunit5.class)
public class OzonSearchBarNegativeUITest {

    private HomePage homePage;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    @Step("Инициализация HomePage перед каждым тестом")
    public void beforeEach(TestInfo testInfo) {
        homePage = new HomePage();
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @Description("Поиск с пустым запросом")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Search with empty query")
    public void testSearchWithEmptyQuery() {
        Allure.step("Получение текущего URL страницы");
        String expectedUrl = Singleton.getDriver().getCurrentUrl();
        Allure.step("Ввод пустой строки в поле поиска");
        homePage.enterSearchQuery("").submitSearchByButton();
        String currentUrl = Singleton.getDriver().getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains(expectedUrl), "URL должен остаться неизменным");
    }

    @Test
    @Description("Поиск с пробелом")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Search with single space")
    public void testSearchWithSingleSpace() {
        Allure.step("Получение текущего URL страницы");
        String expectedUrl = Singleton.getDriver().getCurrentUrl();
        Allure.step("Ввод пробела в поле поиска");
        homePage.enterSearchQuery(" ").submitSearchByButton();
        String currentUrl = Singleton.getDriver().getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains(expectedUrl), "URL должен остаться неизменным");
    }

    @Test
    @Description("Поиск с одним символом")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Search with single character")
    public void testSearchWithSingleCharacter() {
        Allure.step("Получение текущего URL страницы");
        String expectedUrl = Singleton.getDriver().getCurrentUrl();
        Allure.step("Ввод одного символа в поле поиска");
        homePage.enterSearchQuery("a").submitSearchByButton();
        String currentUrl = Singleton.getDriver().getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains(expectedUrl), "URL должен остаться неизменным");
    }

    @Test
    @Description("Поиск по символам")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Search product by symbols")
    public void testSearchProductBySymbols() {
        Allure.step("Генерация случайного набора символов");
        String searchQuery = new Symbol().getRandomValue();
        String expectedResult = Messages.NO_RESULTS.getMessage();
        Allure.step("Ввод набора символов в поле поиска");
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getNoResultsMessage();

        Assertions.assertTrue(result.contains(expectedResult), "Сообщение о том, что результаты отсутствуют, должно появиться");
    }

    @Test
    @Description("Поиск по несуществующему артикулу")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Search product by correct, but non-existent article")
    public void testSearchProductByNonExistentArticle() {
        Allure.step("Генерация несуществующего артикула");
        String searchQuery = new NonExistentArticle().getRandomValue();
        String expectedResult = Messages.PAGE_NOT_FOUND.getMessage();
        Allure.step("Ввод несуществующего артикула");
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getPageNotFoundMessage();

        Assertions.assertEquals(expectedResult, result, "Сообщение о том, что страница не найдена, должно появиться");
    }

    @Test
    @Description("Поиск с максимальной длиной запроса")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Search product with maximum query length")
    public void testSearchProductWithMaxQueryLength() {
        Allure.step("Генерация строки из 255 символов");
        String searchQuery = "9".repeat(255);
        Allure.step("Ввод строки в поле поиска");
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getSearchResultMessage();

        Assertions.assertTrue(result.contains(searchQuery), "Результат поиска должен включать строку длиной 255 символов");
    }

    @Test
    @Description("Поиск с запросом, превышающим максимальную длину")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Search product with query exceeding max length")
    public void testSearchProductWithExceedingQueryLength() {
        Allure.step("Генерация строки из 500 символов");
        String searchQuery = "9".repeat(500);
        Allure.step("Ввод строки в поле поиска");
        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getSearchResultMessage();

        Assertions.assertTrue(result.contains("9".repeat(255)), "Результат поиска должен быть обрезан до 255 символов");
    }

    @AfterEach
    @Step("Завершение теста и завершение работы драйвера")
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
        Singleton.quit();
    }
}
