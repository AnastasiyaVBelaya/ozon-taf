package login.ui;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.enums.Countries;
import by.it_academy.belaya.enums.Messages;
import by.it_academy.belaya.pages.HomePage;
import by.it_academy.belaya.pages.LoginPage;
import by.it_academy.belaya.testdata.Symbol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

public class OzonLoginNegativeUITest {
    private LoginPage loginPage;
    private static final Logger logger = LogManager.getLogger();

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        HomePage homePage = new HomePage();
        loginPage = homePage.openLoginPage();
        logger.info("Starting test: {}", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Login by phone number with empty query")
    public void testLoginByPhoneNumberWithEmptyQuery() {
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber("")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by phone number with single space character")
    public void testLoginByPhoneNumberWithSingleSpace() {
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(" ")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }


    @Test
    @DisplayName("Login by phone number with single digit")
    public void testLoginByPhoneNumberWithSingleDigit() {
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber("1")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by phone number with letters")
    public void testLoginByPhoneNumberWithSingleCharacter() {
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber("button")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by invalid phone number - check why can't I sign in button")
    public void testLoginByInvalidPhoneNumberWhyCantISignInButton() {
        String number = "0".repeat(10);

        Assertions.assertTrue(loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(number)
                .clickOnSignInButton()
                .getWhyCantISighInButton()
                .isEnabled());
    }

    @Test
    @DisplayName("Login by invalid phone number - check incorrect phone format message")
    public void testLoginByInvalidPhoneNumberIncorrectPhoneFormatMessage() {
        String number = "0".repeat(10);
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(number)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by too long phone number")
    public void testLoginByTooLongPhoneNumberIncorrectPhoneFormatMessage() {
        String number = "9".repeat(255);
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(number)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by phone number with symbols")
    public void testLoginByPhoneNumberWithSymbols() {
        String number = new Symbol().getRandomValue();
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(number)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by phone number with HTML tags")
    public void testLoginByPhoneNumberWithHTMLTags() {
        String number = "<script>alert('test');</script>";
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(number)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by phone number with XSS attack (script tag)")
    public void testLoginByPhoneNumberWithXSSAttack() {
        String number = "<script>alert('XSS');</script>";
        String result = loginPage
                .selectCountryFromDropDown(Countries.BELARUS)
                .enterPhoneNumber(number)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_PHONE_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by email with empty query")
    public void testLoginByEmailWithEmptyQuery() {
        String result = loginPage
                .clickOnSignByEmailButton()
                .enterEmail("")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.BLANK_EMAIL.getMessage(), result);
    }

    @Test
    @DisplayName("Login by email with single space character")
    public void testLoginByEmailWithSingleSpaceCharacter() {
        String result = loginPage
                .clickOnSignByEmailButton()
                .enterEmail(" ")
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.BLANK_EMAIL.getMessage(), result);
    }

    @Test
    @DisplayName("Login by too long email")
    public void testLoginByTooLongEmail() {
        String email = "S".repeat(255)+"@gmail.com";
        String result = loginPage
                .clickOnSignByEmailButton()
                .enterEmail(email)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_MAIL_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by invalid email - check why can't I sign in button")
    public void testLoginByInvalidEmailWhyCantISignInButton() {
        String email = "testgmail.com";

        Assertions.assertTrue(loginPage
                .clickOnSignByEmailButton()
                .enterEmail(email)
                .clickOnSignInButton()
                .getWhyCantISighInButton()
                .isEnabled());
    }

    @Test
    @DisplayName("Login by valid but not registered email")
    public void testLoginByValidNotRegisteredEmail() {
        String email = "test@gmail.com";
        String result = loginPage
                .clickOnSignByEmailButton()
                .enterEmail(email)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.NO_ACCOUNT_WITH_SUCH_EMAIL.getMessage(), result);
    }

    @Test
    @DisplayName("Login by invalid email")
    public void testLoginByInvalidEmail() {
        String email = "testgmail.com";
        String result = loginPage
                .clickOnSignByEmailButton()
                .enterEmail(email)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_MAIL_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by email with HTML tags")
    public void testLoginByEmailWithHTMLTags() {
        String email = "<script>alert('test');</script>";
        String result = loginPage
                .clickOnSignByEmailButton()
                .enterEmail(email)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_MAIL_FORMAT.getMessage(), result);
    }

    @Test
    @DisplayName("Login by email with XSS attack (script tag)")
    public void testLoginByEmailWithXSSAttack() {
        String email = "<script>alert('XSS');</script>";
        String result = loginPage
                .clickOnSignByEmailButton()
                .enterEmail(email)
                .clickOnSignInButton()
                .getIncorrectInputMessage()
                .getText();

        Assertions.assertEquals(Messages.INCORRECT_MAIL_FORMAT.getMessage(), result);
    }

    @AfterEach
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
        Singleton.quit();
    }
}
