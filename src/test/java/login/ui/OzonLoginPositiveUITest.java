package login.ui;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.enums.Countries;
import by.it_academy.belaya.pages.HomePage;
import by.it_academy.belaya.pages.LoginPage;
import by.it_academy.belaya.testdata.ValidEmails;
import by.it_academy.belaya.utils.TestDataUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

public class OzonLoginPositiveUITest {
    private LoginPage loginPage;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        HomePage homePage = new HomePage();
        loginPage = homePage.openLoginPage();
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Check login page opens")
    public void testLoginPageOpens() {
        Assertions.assertTrue(loginPage.getCustomerHelpCenterLink().isEnabled());
    }

    @Test
    @DisplayName("Check phone input field enabled")
    public void testPhoneInputFieldEnabled() {
        Assertions.assertTrue(loginPage
                .getPhoneInputField()
                .isEnabled());
    }

    @Test
    @DisplayName("Login by valid phone number")
    public void testLoginByValidPhoneNumber() {
        String phoneNumber = TestDataUtils.generatePhoneNumber(Countries.BELARUS);

        Assertions.assertTrue(loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(phoneNumber)
                .clickOnSignInButton()
                .getVerificationCodeField().isEnabled());
    }

    @Test
    @DisplayName("Login by phone number with hyphens")
    public void testLoginByPhoneNumberWithHyphens() {
        String phoneNumber = TestDataUtils.generatePhoneNumber(Countries.BELARUS);
        String formattedNumber = TestDataUtils.formatPhoneNumberWithHyphens(phoneNumber);
        Assertions.assertTrue(loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(formattedNumber)
                .clickOnSignInButton()
                .getVerificationCodeField()
                .isEnabled());
    }

    @Test
    @DisplayName("Login by phone number with blanks")
    public void testLoginByPhoneNumberWithBlanks() {
        String phoneNumber = TestDataUtils.generatePhoneNumber(Countries.BELARUS);
        String formattedNumber = TestDataUtils.formatPhoneNumberWithBlanks(phoneNumber);
        Assertions.assertTrue(loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(formattedNumber)
                .clickOnSignInButton()
                .getVerificationCodeField()
                .isEnabled());
    }

    @Test
    @DisplayName("Login by phone number with leading and trailing spaces")
    public void testLoginByPhoneNumberWithLeadingAndTrailingBlanks() {
        String phoneNumber = TestDataUtils.generatePhoneNumber(Countries.BELARUS);
        String number = TestDataUtils.generateSearchQueryWithRandomSpaces(phoneNumber);

        Assertions.assertTrue(loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(number)
                .clickOnSignInButton()
                .getVerificationCodeField()
                .isEnabled());
    }

    @Test
    @DisplayName("Login by valid email")
    public void testLoginByValidEmail() {
        String email = new ValidEmails().getRandomValue();

        Assertions.assertTrue(loginPage
                .clickOnSignByEmailButton()
                .enterEmail(email)
                .clickOnSignInButton()
                .getVerificationCodeField().isEnabled());
    }

    @Test
    @DisplayName("Test Sign in with Apple opens new window")
    public void testSignInWithAppleOpensNewWindow() {
        boolean result = loginPage
                .clickOnSignByAppleButton()
                .switchToNewWindow()
                .isAppleIdPageOpened();
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Check inputEmailOrPhoneField enabled")
    public void testInputEmailOrPhoneFieldEnabled() {
        Assertions.assertTrue(loginPage
                .clickOnSignByAppleButton()
                .switchToNewWindow()
                .getInputEmailOrPhoneField()
                .isEnabled());
    }

    @Test
    @DisplayName("Check inputPasswordField enabled")
    public void testInputPasswordFieldEnabled() {
        String email = new ValidEmails().getRandomValue();

        Assertions.assertTrue(loginPage
                .clickOnSignByAppleButton()
                .switchToNewWindow()
                .enterEmailOrPhone(email)
                .getInputPasswordField()
                .isEnabled());
    }

    @Test
    @DisplayName("Check recoveryPage opens")
    public void testRecoveryPageOpens() {
        Assertions.assertTrue(loginPage
                .clickOnICantSignInButton()
                .isRecoveryPageOpened());
    }

    @Test
    @DisplayName("Check firstReason from Recovery Page enabled")
    public void testFirstReasonIsEnabled() {
        Assertions.assertTrue(loginPage
                .clickOnICantSignInButton()
                .getFirstReasonButton().isEnabled());
    }

    @Test
    @DisplayName("Check secondReason from Recovery Page enabled")
    public void testSecondReasonEnabled() {
        Assertions.assertTrue(loginPage
                .clickOnICantSignInButton()
                .getSecondReasonButton().isEnabled());
    }

    @AfterEach
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
        Singleton.quit();
    }
}
