package login.api;

import by.it_academy.belaya.testdata.*;
import by.it_academy.belaya.utils.Endpoints;
import by.it_academy.belaya.utils.Steps;
import by.it_academy.belaya.utils.TestDataUtils;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class TheTwentyFirstCenturyLoginNegativeAPITest {

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
    @DisplayName("Login by valid email and random password")
    public void testLoginByValidEmailAndRandomPassword() {
        RequestBodyForLoginWithValidEmailAndRandomPassword requestBody =
                RequestBodyForLoginWithValidEmailAndRandomPassword.create();
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Неверный пароль");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Неверный пароль");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by random email and password")
    public void testLoginByRandomEmailAndPassword() {
        RequestBodyForRandomNonRegisteredEmailAndPassword requestBody =
                RequestBodyForRandomNonRegisteredEmailAndPassword.create();
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());


        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by empty email and password")
    public void testLoginByEmptyEmailAndPassword() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("", "");

        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Поле email является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Поле email является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertThat(response.jsonPath().getString("errors[1].title"))
                .isEqualTo("Поле password является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[1].detail"))
                .isEqualTo("Поле password является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[1].source.pointer"))
                .isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by empty body")
    public void testLoginByEmptyBody() {
        String requestBody = "{}";
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody);

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Поле email является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Поле email является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertThat(response.jsonPath().getString("errors[1].title"))
                .isEqualTo("Поле password является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[1].detail"))
                .isEqualTo("Поле password является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[1].source.pointer"))
                .isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by email without domain")
    public void testLoginByEmailWithoutDomain() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail@",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by email without @ symbol")
    public void testLoginByEmailWithoutAtSymbol() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail.mailru",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by email with multiple @ symbols")
    public void testLoginByEmailWithMultipleAtSymbols() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail@testemail@mailru",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by email with space before @ symbol")
    public void testLoginByEmailWithSpaceBeforeAtSymbol() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail @mailru",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by email with space after @ symbol")
    public void testLoginByEmailWithSpaceAfterAtSymbol() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail@mail ru",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by email with invalid domain")
    public void testLoginByEmailWithInvalidDomain() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail@mail",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Неверный формат поля email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by email with special characters")
    public void testLoginByEmailWithSpecialCharacters() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("teste$mail#mailru@mail.ru",
                        TestDataUtils.generateRandomPassword());
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login with too long email")
    public void testLoginWithTooLongEmail() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("d" .repeat(256) + "@mail.ru",
                        TestDataUtils.generateRandomPassword());

        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Длина поля email не должна превышать 255 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Длина поля email не должна превышать 255 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login with too long password")
    public void testLoginWithTooLongPassword() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword(TestDataUtils.generateRandomEmail(),
                        "d" .repeat(256));
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Длина поля password должна быть от 6 до 32 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Длина поля password должна быть от 6 до 32 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login with email containing Cyrillic characters")
    public void testLoginWithEmailContainingCyrillic() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("эмэйл@mail.ru",
                        TestDataUtils.generateRandomPassword());
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @DisplayName("Login with short password")
    public void testLoginWithShortPassword() {
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword(TestDataUtils.generateRandomEmail(), "123");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service"))
                .isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Длина поля password должна быть от 6 до 32 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Длина поля password должна быть от 6 до 32 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @DisplayName("Test invalid phone format in request")
    public void testInvalidPhoneFormat() {
        runInvalidPhoneTest("+375 () 7412681");
    }

    @Test
    @DisplayName("Test invalid phone format - missing country code")
    public void testInvalidPhoneMissingCountryCode() {
        runInvalidPhoneTest("44 7412681");
    }

    @Test
    @DisplayName("Test invalid phone format - incorrect symbols")
    public void testInvalidPhoneWithIncorrectSymbols() {
        runInvalidPhoneTest("+375-44-741@2681");
    }

    @Test
    @DisplayName("Test invalid phone format - extra spaces")
    public void testInvalidPhoneWithExtraSpaces() {
        runInvalidPhoneTest("+375   (44)  7412681");
    }

    @Test
    @DisplayName("Test invalid phone format - missing digits")
    public void testInvalidPhoneWithMissingDigits() {
        runInvalidPhoneTest("+375 (44) 741");
    }

    @Test
    @DisplayName("Test invalid phone format - too long")
    public void testInvalidPhoneTooLong() {
        RequestBodyForLoginByInvalidPhone requestBody =
                new RequestBodyForLoginByInvalidPhone("+375 (44) 74126811234897654324567890876543245675");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("account");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Длина телефона не может превышать 25 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Длина телефона не может превышать 25 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("phone");
        softly.assertAll();
    }

    @Test
    @DisplayName("Test invalid phone format - special characters")
    public void testInvalidPhoneWithSpecialCharacters() {
        runInvalidPhoneTest("+375 (44) #$@!%2681");
    }

    @Test
    @DisplayName("Test invalid phone format - letters")
    public void testInvalidPhoneWithLetters() {
        runInvalidPhoneTest("+375 (44) ABCDEFG");
    }

    @Test
    @DisplayName("Test invalid phone format - empty string")
    public void testInvalidPhoneEmptyString() {
        RequestBodyForLoginByInvalidPhone requestBody = new RequestBodyForLoginByInvalidPhone("");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), requestBody.getBody());
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("account");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Поле phone является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Поле phone является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("phone");
        softly.assertAll();
    }

    @Test
    @DisplayName("Test invalid phone format - empty body")
    public void testInvalidPhoneEmptyBody() {
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), "{}");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("account");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Поле phone является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Поле phone является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer"))
                .isEqualTo("phone");
        softly.assertAll();
    }

    @Test
    @DisplayName("Test invalid phone format - null value")
    public void testInvalidPhoneNull() {
        RequestBodyForLoginByInvalidPhone requestBody = new RequestBodyForLoginByInvalidPhone(null);
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), requestBody.getBody());
        softly.assertThat(response.statusCode()).isEqualTo(503);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("account");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Системная ошибка");
        softly.assertAll();
    }

    private void runInvalidPhoneTest(String phone) {
        RequestBodyForLoginByInvalidPhone requestBody = new RequestBodyForLoginByInvalidPhone(phone);
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), requestBody.getBody());
        assertInvalidPhoneResponse(response);
    }

    private void assertInvalidPhoneResponse(Response response) {
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("account");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Неправильный формат номера телефона");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Неправильный формат номера телефона");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer")).isEqualTo("phone");
        softly.assertAll();
    }

    @AfterEach
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}

