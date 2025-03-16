package login.ui;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.enums.Countries;
import by.it_academy.belaya.enums.Messages;
import by.it_academy.belaya.pages.HomePage;
import by.it_academy.belaya.pages.LoginPage;
import by.it_academy.belaya.testdata.Symbol;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("Login UI Tests")
@Feature("Negative Login Scenarios")
@ExtendWith(AllureJunit5.class)
public class OzonLoginNegativeUITest {
    private LoginPage loginPage;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    @Step("Открытие страницы логина перед каждым тестом")
    public void beforeEach(TestInfo testInfo) {
        HomePage homePage = new HomePage();
        loginPage = homePage.openLoginPage();
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @Description("Авторизация по номеру телефона с пустым запросом")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by phone number with empty query")
    public void testLoginByPhoneNumberWithEmptyQuery() {
        Allure.step("Ввод пустого номера телефона");
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber("")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Allure.step("Проверка сообщения об ошибке");
        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @Description("Авторизация по номеру телефона с одним пробелом")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by phone number with single space character")
    public void testLoginByPhoneNumberWithSingleSpace() {
        Allure.step("Ввод номера телефона, содержащего только пробел");
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(" ")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Allure.step("Проверка сообщения об ошибке");
        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @Description("Авторизация по номеру телефона с одной цифрой")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login by phone number with single digit")
    public void testLoginByPhoneNumberWithSingleDigit() {
        Allure.step("Ввод номера телефона, состоящего из одной цифры");
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber("1")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Allure.step("Проверка сообщения об ошибке");
        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @Description("Авторизация по номеру телефона с текстом")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Login by phone number with letters")
    public void testLoginByPhoneNumberWithSingleCharacter() {
        Allure.step("Ввод номера телефона с буквами");
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber("button")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Allure.step("Проверка сообщения об ошибке");
        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @Description("Авторизация с использованием слишком длинного номера телефона")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login by too long phone number")
    public void testLoginByTooLongPhoneNumberIncorrectPhoneFormatMessage() {
        Allure.step("Генерация номера телефона длиной 255 символов");
        String number = "9".repeat(255);

        Allure.step("Ввод слишком длинного номера телефона");
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(number)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Allure.step("Проверка сообщения об ошибке");
        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @Description("Авторизация с пустым email")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by email with empty query")
    public void testLoginByEmailWithEmptyQuery() {
        Allure.step("Ввод пустого email");
        String result = loginPage
                .clickOnSignByEmailButton()
                .enterEmail("")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Allure.step("Проверка сообщения об ошибке");
        Assertions.assertEquals(Messages.BLANK_EMAIL.getMessage(), result);
    }

    @AfterEach
    @Step("Завершение теста и завершение работы драйвера")
    public void tearDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
        Singleton.quit();
    }
}
