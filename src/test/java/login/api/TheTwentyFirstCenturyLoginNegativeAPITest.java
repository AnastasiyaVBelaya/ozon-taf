package login.api;

import by.it_academy.belaya.testdata.*;
import by.it_academy.belaya.utils.Endpoints;
import by.it_academy.belaya.utils.Steps;
import by.it_academy.belaya.utils.TestDataUtils;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

@Epic("Login API Tests")
@Feature("Negative Login Scenarios")
@ExtendWith(AllureJunit5.class)
public class TheTwentyFirstCenturyLoginNegativeAPITest {

    private static final Logger logger = LogManager.getLogger();
    private SoftAssertions softly;

    @BeforeAll
    @Step("Загрузка заголовков перед началом всех тестов")
    public static void beforeAll() throws IOException {
        Steps.loadHeaders();
    }

    @BeforeEach
    @Step("Подготовка перед каждым тестом")
    public void beforeEach(TestInfo testInfo) {
        logger.info("Starting test: {}", testInfo.getDisplayName());
        softly = new SoftAssertions();
    }

    @Test
    @Description("Авторизация с валидным email и случайным паролем")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by valid email and random password")
    public void testLoginByValidEmailAndRandomPassword() {
        Allure.step("Создание тела запроса для валидного email и случайного пароля");
        RequestBodyForLoginWithValidEmailAndRandomPassword requestBody =
                RequestBodyForLoginWithValidEmailAndRandomPassword.create();

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и ошибок в ответе");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title")).isEqualTo("Неверный пароль");
        softly.assertThat(response.jsonPath().getString("errors[0].detail")).isEqualTo("Неверный пароль");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer")).isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @Description("Авторизация с случайным email и паролем")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by random email and password")
    public void testLoginByRandomEmailAndPassword() {
        Allure.step("Создание тела запроса для случайного email и пароля");
        RequestBodyForRandomNonRegisteredEmailAndPassword requestBody =
                RequestBodyForRandomNonRegisteredEmailAndPassword.create();

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и ошибок в ответе");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title")).isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail")).isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer")).isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @Description("Авторизация с пустыми email и паролем")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by empty email and password")
    public void testLoginByEmptyEmailAndPassword() {
        Allure.step("Создание тела запроса с пустыми email и паролем");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("", "");

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и ошибок в ответе");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Поле email является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Поле email является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer")).isEqualTo("email");
        softly.assertThat(response.jsonPath().getString("errors[1].title"))
                .isEqualTo("Поле password является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[1].detail"))
                .isEqualTo("Поле password является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[1].source.pointer")).isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @Description("Авторизация с пустым телом запроса")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by empty body")
    public void testLoginByEmptyBody() {
        Allure.step("Создание пустого тела запроса");
        String requestBody = "{}";

        Allure.step("Отправка пустого запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody);

        Allure.step("Проверка кода статуса и ошибок в ответе");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Поле email является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Поле email является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer")).isEqualTo("email");
        softly.assertThat(response.jsonPath().getString("errors[1].title"))
                .isEqualTo("Поле password является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[1].detail"))
                .isEqualTo("Поле password является обязательным полем");
        softly.assertThat(response.jsonPath().getString("errors[1].source.pointer")).isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @Description("Авторизация с email без домена")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by email without domain")
    public void testLoginByEmailWithoutDomain() {
        Allure.step("Создание тела запроса с email без домена");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail@",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщения об ошибке");
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
    @Description("Авторизация с email без символа @")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by email without @ symbol")
    public void testLoginByEmailWithoutAtSymbol() {
        Allure.step("Создание тела запроса с email без символа @");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail.mailru",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщения об ошибке");
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
    @Description("Авторизация с email, содержащим несколько символов @")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by email with multiple @ symbols")
    public void testLoginByEmailWithMultipleAtSymbols() {
        Allure.step("Создание тела запроса с email с несколькими символами @");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail@testemail@mailru",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщения об ошибке");
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
    @Description("Авторизация с email с пробелом перед символом @")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Login by email with space before @ symbol")
    public void testLoginByEmailWithSpaceBeforeAtSymbol() {
        Allure.step("Создание тела запроса с email, содержащим пробел перед символом @");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail @mailru",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщения об ошибке");
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
    @Description("Авторизация с email, содержащим пробел после символа @")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by email with space after @ symbol")
    public void testLoginByEmailWithSpaceAfterAtSymbol() {
        Allure.step("Создание тела запроса с email, содержащим пробел после символа @");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail@mail ru",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщения об ошибке");
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
    @Description("Авторизация с email с некорректным доменом")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by email with invalid domain")
    public void testLoginByEmailWithInvalidDomain() {
        Allure.step("Создание тела запроса с email с некорректным доменом");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("testemail@mail",
                        System.getenv("21_VEK_LOGIN_PASSWORD"));

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщения об ошибке");
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
    @Description("Авторизация с email, содержащим специальные символы")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Login by email with special characters")
    public void testLoginByEmailWithSpecialCharacters() {
        Allure.step("Создание тела запроса с email, содержащим специальные символы");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("teste$mail#mailru@mail.ru",
                        TestDataUtils.generateRandomPassword());

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщения об ошибке");
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
    @Description("Авторизация с email, содержащим слишком длинный адрес")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login with too long email")
    public void testLoginWithTooLongEmail() {
        Allure.step("Создание тела запроса с email, длина которого превышает 255 символов");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("d".repeat(256) + "@mail.ru",
                        TestDataUtils.generateRandomPassword());

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщения об ошибке");
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
    @Description("Авторизация с слишком длинным паролем")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login with too long password")
    public void testLoginWithTooLongPassword() {
        Allure.step("Создание тела запроса с слишком длинным паролем");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword(TestDataUtils.generateRandomEmail(), "d".repeat(256));

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL), requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщений об ошибке");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Длина поля password должна быть от 6 до 32 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Длина поля password должна быть от 6 до 32 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer")).isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @Description("Авторизация с email, содержащим кириллические символы")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Login with email containing Cyrillic characters")
    public void testLoginWithEmailContainingCyrillic() {
        Allure.step("Создание тела запроса с email, содержащим кириллические символы");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword("эмэйл@mail.ru", TestDataUtils.generateRandomPassword());

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL), requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщений об ошибке");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title")).isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].detail")).isEqualTo("Проверьте email");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer")).isEqualTo("email");
        softly.assertAll();
    }

    @Test
    @Description("Авторизация с коротким паролем")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login with short password")
    public void testLoginWithShortPassword() {
        Allure.step("Создание тела запроса с коротким паролем");
        RequestBodyForLoginByInvalidEmailAndPassword requestBody =
                new RequestBodyForLoginByInvalidEmailAndPassword(TestDataUtils.generateRandomEmail(), "123");

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL), requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщений об ошибке");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("sso");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Длина поля password должна быть от 6 до 32 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Длина поля password должна быть от 6 до 32 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer")).isEqualTo("password");
        softly.assertAll();
    }

    @Test
    @Description("Проверка недействительного формата телефона в запросе")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test invalid phone format in request")
    public void testInvalidPhoneFormat() {
        Allure.step("Выполнение теста с недействительным форматом телефона");
        runInvalidPhoneTest("+375 () 7412681");
    }

    @Test
    @Description("Проверка недействительного формата телефона - отсутствует код страны")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test invalid phone format - missing country code")
    public void testInvalidPhoneMissingCountryCode() {
        Allure.step("Выполнение теста с телефоном без кода страны");
        runInvalidPhoneTest("44 7412681");
    }

    @Test
    @Description("Проверка недействительного формата телефона - некорректные символы")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test invalid phone format - incorrect symbols")
    public void testInvalidPhoneWithIncorrectSymbols() {
        Allure.step("Выполнение теста с телефоном, содержащим некорректные символы");
        runInvalidPhoneTest("+375-44-741@2681");
    }

    @Test
    @Description("Проверка недействительного формата телефона - слишком длинный номер")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test invalid phone format - too long")
    public void testInvalidPhoneTooLong() {
        Allure.step("Создание тела запроса с слишком длинным номером телефона");
        RequestBodyForLoginByInvalidPhone requestBody =
                new RequestBodyForLoginByInvalidPhone("+375 (44) 74126811234897654324567890876543245675");

        Allure.step("Отправка запроса авторизации с недействительным телефоном");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщений об ошибке");
        softly.assertThat(response.statusCode()).isEqualTo(422);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("account");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Длина телефона не может превышать 25 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].detail"))
                .isEqualTo("Длина телефона не может превышать 25 символов");
        softly.assertThat(response.jsonPath().getString("errors[0].source.pointer")).isEqualTo("phone");
        softly.assertAll();
    }
    @Test
    @Description("Проверка недействительного формата телефона со специальными символами")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test invalid phone format - special characters")
    public void testInvalidPhoneWithSpecialCharacters() {
        Allure.step("Запуск теста с телефоном, содержащим специальные символы");
        runInvalidPhoneTest("+375 (44) #$@!%2681");
    }

    @Test
    @Description("Проверка недействительного формата телефона с буквами")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test invalid phone format - letters")
    public void testInvalidPhoneWithLetters() {
        Allure.step("Запуск теста с телефоном, содержащим буквы");
        runInvalidPhoneTest("+375 (44) ABCDEFG");
    }

    @Test
    @Description("Проверка недействительного формата телефона с пустой строкой")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test invalid phone format - empty string")
    public void testInvalidPhoneEmptyString() {
        Allure.step("Создание тела запроса с пустой строкой в поле телефона");
        RequestBodyForLoginByInvalidPhone requestBody = new RequestBodyForLoginByInvalidPhone("");

        Allure.step("Отправка запроса авторизации с пустой строкой в поле телефона");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщений об ошибке");
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
    @Description("Проверка недействительного формата телефона с пустым телом запроса")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test invalid phone format - empty body")
    public void testInvalidPhoneEmptyBody() {
        Allure.step("Отправка запроса авторизации с пустым телом");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), "{}");

        Allure.step("Проверка кода статуса и сообщений об ошибке");
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
    @Description("Проверка недействительного формата телефона со значением null")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Test invalid phone format - null value")
    public void testInvalidPhoneNull() {
        Allure.step("Создание тела запроса с null-значением в поле телефона");
        RequestBodyForLoginByInvalidPhone requestBody = new RequestBodyForLoginByInvalidPhone(null);

        Allure.step("Отправка запроса авторизации с null-значением");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), requestBody.getBody());

        Allure.step("Проверка кода статуса и сообщений об ошибке");
        softly.assertThat(response.statusCode()).isEqualTo(503);
        softly.assertThat(response.jsonPath().getString("service")).isEqualTo("account");
        softly.assertThat(response.jsonPath().getString("errors[0].title"))
                .isEqualTo("Системная ошибка");
        softly.assertAll();
    }

    private void runInvalidPhoneTest(String phone) {
        Allure.step("Создание тела запроса с недействительным телефоном: " + phone);
        RequestBodyForLoginByInvalidPhone requestBody = new RequestBodyForLoginByInvalidPhone(phone);

        Allure.step("Отправка запроса авторизации");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE), requestBody.getBody());

        Allure.step("Проверка ответа на недействительный формат телефона");
        assertInvalidPhoneResponse(response);
    }

    private void assertInvalidPhoneResponse(Response response) {
        Allure.step("Проверка, что ответ содержит код ошибки 422 и правильное сообщение");
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
    @Step("Завершение теста")
    public void tearDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}

