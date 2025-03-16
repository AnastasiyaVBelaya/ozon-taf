package search.ui;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.pages.HomePage;
import by.it_academy.belaya.testdata.ArticleNumber;
import by.it_academy.belaya.testdata.ProductName;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("Ozon UI Tests")
@Feature("Search Bar Functionality")
@ExtendWith(AllureJunit5.class)
public class OzonSearchBarPositiveUITest {

    private HomePage homePage;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    @Step("Инициализация HomePage перед каждым тестом")
    public void beforeEach(TestInfo testInfo) {
        homePage = new HomePage();
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @Description("Проверка доступности поисковой строки")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Check search bar enabled")
    @Step("Тест проверяет, что поисковая строка активна")
    public void testSearchBarEnabled() {
        Assertions.assertTrue(homePage
                .getSearchInputField()
                .isEnabled(), "Search bar should be enabled");
    }

    @Test
    @Description("Поиск продукта по действительному артикулу с кнопкой")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Search product by valid article (by button)")
    public void testSearchProductByValidArticleByButton() {
        String searchQuery = new ArticleNumber().getRandomValue();
        Allure.step("Получение случайного артикульного номера: " + searchQuery);

        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getValidArticleSearchResult();

        Assertions.assertTrue(result.contains(searchQuery), "The search result should contain the article number");
    }

    @Test
    @Description("Поиск продукта по действительному артикулу с клавишей Enter")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Search product by valid article (by Enter)")
    public void testSearchProductByValidArticleByEnter() {
        String searchQuery = new ArticleNumber().getRandomValue();
        Allure.step("Получение случайного артикульного номера: " + searchQuery);

        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByEnter()
                .getValidArticleSearchResult();

        Assertions.assertTrue(result.contains(searchQuery), "The search result should contain the article number");
    }

    @Test
    @Description("Поиск продукта по названию товара")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Search by product name")
    public void testSearchByProductName() {
        String searchQuery = new ProductName().getRandomValue();
        Allure.step("Получение случайного названия продукта: " + searchQuery);

        String result = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getSearchResultTitle();

        Assertions.assertTrue(result.toLowerCase().contains(searchQuery.toLowerCase()),
                "The search result title should contain the product name");
    }

    @Test
    @Description("Сообщение с результатами поиска содержит название продукта")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Search result message contains product name")
    public void testSearchResultMessageContainsProductName() {
        String searchQuery = new ProductName().getRandomValue();
        Allure.step("Получение случайного названия продукта: " + searchQuery);

        String resultMessage = homePage
                .enterSearchQuery(searchQuery)
                .submitSearchByButton()
                .getSearchResultMessage();

        Assertions.assertTrue(resultMessage.contains(searchQuery),
                "The search result message should contain the product name");
    }

    @AfterEach
    @Step("Завершение теста и закрытие драйвера")
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
        Singleton.quit();
    }
}
