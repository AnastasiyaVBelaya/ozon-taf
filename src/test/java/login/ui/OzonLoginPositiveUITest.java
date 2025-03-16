package login.ui;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.enums.Countries;
import by.it_academy.belaya.pages.HomePage;
import by.it_academy.belaya.pages.LoginPage;
import by.it_academy.belaya.testdata.ValidEmails;
import by.it_academy.belaya.utils.TestDataUtils;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("Login UI Tests")
@Feature("Positive Login Scenarios")
@ExtendWith(AllureJunit5.class)
public class OzonLoginPositiveUITest {
    private LoginPage loginPage;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    @Step("Открытие страницы логина перед тестом")
    public void beforeEach(TestInfo testInfo) {
        HomePage homePage = new HomePage();
        loginPage = homePage.openLoginPage();
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @Description("Проверка открытия страницы логина")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Check login page opens")
    public void testLoginPageOpens() {
        Allure.step("Проверка, что ссылка на центр помощи клиента доступна");
        Assertions.assertTrue(loginPage.getCustomerHelpCenterLink().isEnabled());
    }

    @Test
    @Description("Проверка доступности поля ввода телефона")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Check phone input field enabled")
    public void testPhoneInputFieldEnabled() {
        Allure.step("Проверка, что поле ввода телефона активно");
        Assertions.assertTrue(loginPage
                .getPhoneInputField()
                .isEnabled());
    }

    @Test
    @Description("Авторизация с использованием действительного номера телефона")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Login by valid phone number")
    public void testLoginByValidPhoneNumber() {
        String phoneNumber = TestDataUtils.generatePhoneNumber(Countries.BELARUS);
        Allure.step("Генерация действительного номера телефона для Беларуси: " + phoneNumber);

        Allure.step("Ввод номера телефона и проверка доступности поля ввода кода верификации");
        Assertions.assertTrue(loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(phoneNumber)
                .clickOnSignInButton()
                .getVerificationCodeField().isEnabled());
    }

    @Test
    @Description("Авторизация с использованием номера телефона с дефисами")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login by phone number with hyphens")
    public void testLoginByPhoneNumberWithHyphens() {
        String phoneNumber = TestDataUtils.generatePhoneNumber(Countries.BELARUS);
        String formattedNumber = TestDataUtils.formatPhoneNumberWithHyphens(phoneNumber);
        Allure.step("Форматирование номера телефона с дефисами: " + formattedNumber);

        Assertions.assertTrue(loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(formattedNumber)
                .clickOnSignInButton()
                .getVerificationCodeField()
                .isEnabled());
    }

    @Test
    @Description("Авторизация с использованием номера телефона с пробелами")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login by phone number with blanks")
    public void testLoginByPhoneNumberWithBlanks() {
        String phoneNumber = TestDataUtils.generatePhoneNumber(Countries.BELARUS);
        String formattedNumber = TestDataUtils.formatPhoneNumberWithBlanks(phoneNumber);
        Allure.step("Форматирование номера телефона с пробелами: " + formattedNumber);

        Assertions.assertTrue(loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(formattedNumber)
                .clickOnSignInButton()
                .getVerificationCodeField()
                .isEnabled());
    }

    @Test
    @Description("Авторизация с использованием номера телефона с ведущими и завершающими пробелами")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login by phone number with leading and trailing spaces")
    public void testLoginByPhoneNumberWithLeadingAndTrailingBlanks() {
        String phoneNumber = TestDataUtils.generatePhoneNumber(Countries.BELARUS);
        String number = TestDataUtils.generateSearchQueryWithRandomSpaces(phoneNumber);
        Allure.step("Форматирование номера телефона с ведущими и завершающими пробелами: " + number);

        Assertions.assertTrue(loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(number)
                .clickOnSignInButton()
                .getVerificationCodeField()
                .isEnabled());
    }

    @Test
    @Description("Авторизация с использованием действительного адреса электронной почты")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Login by valid email")
    public void testLoginByValidEmail() {
        String email = new ValidEmails().getRandomValue();
        Allure.step("Генерация действительного адреса электронной почты: " + email);

        Assertions.assertTrue(loginPage
                .clickOnSignByEmailButton()
                .enterEmail(email)
                .clickOnSignInButton()
                .getVerificationCodeField().isEnabled());
    }

    @Test
    @Description("Проверка открытия нового окна при входе через Apple ID")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Test Sign in with Apple opens new window")
    public void testSignInWithAppleOpensNewWindow() {
        Allure.step("Клик по кнопке входа через Apple ID");
        boolean result = loginPage
                .clickOnSignByAppleButton()
                .switchToNewWindow()
                .isAppleIdPageOpened();

        Assertions.assertTrue(result);
    }

    @AfterEach
    @Step("Завершение теста и закрытие драйвера")
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
        Singleton.quit();
    }
}
