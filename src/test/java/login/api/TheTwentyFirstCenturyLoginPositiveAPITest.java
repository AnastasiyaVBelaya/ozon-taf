package login.api;

import by.it_academy.belaya.testdata.*;
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

@Epic("Login API Tests")
@Feature("Positive Login Scenarios")
@ExtendWith(AllureJunit5.class)
public class TheTwentyFirstCenturyLoginPositiveAPITest {

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
    @Description("Авторизация с использованием валидного номера телефона")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by valid phone")
    public void testLoginByValidPhone() {
        Allure.step("Создание тела запроса для авторизации по номеру телефона");
        RequestBodyForLoginByValidPhone requestBodyForLoginByValidPhone =
                RequestBodyForLoginByValidPhone.create();

        Allure.step("Отправка запроса авторизации с номером телефона");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE),
                requestBodyForLoginByValidPhone.getBody());

        Allure.step("Проверка кода ответа и параметра lifetime_remaining");
        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(response.jsonPath().getInt("data.lifetime_remaining")).isEqualTo(120);
        softly.assertAll();
    }

    @Test
    @Description("Авторизация с использованием валидной электронной почты и пароля")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login by valid email and password")
    public void testLoginByValidEmailAndPassword() {
        Allure.step("Создание тела запроса для авторизации по email и паролю");
        RequestBodyForLoginByValidEmailAndPassword requestBody =
                RequestBodyForLoginByValidEmailAndPassword.create();

        Allure.step("Отправка запроса авторизации с email и паролем");
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        Allure.step("Проверка кода ответа и корректности email в ответе");
        softly.assertThat(response.statusCode()).isEqualTo(200);
        String emailFromResponse = response.jsonPath().getString("data.email");
        softly.assertThat(emailFromResponse).isEqualTo(requestBody.getEmail());
        softly.assertAll();
    }

    @AfterEach
    @Step("Завершение теста")
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());
    }
}
