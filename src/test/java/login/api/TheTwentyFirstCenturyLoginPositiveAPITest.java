package login.api;

import by.it_academy.belaya.testdata.*;
import by.it_academy.belaya.utils.Endpoints;
import by.it_academy.belaya.utils.Steps;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class TheTwentyFirstCenturyLoginPositiveAPITest {

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
    @DisplayName("Login by valid phone")
    public void testLoginByValidPhone() {
        RequestBodyForLoginByValidPhone requestBodyForLoginByValidPhone =
                RequestBodyForLoginByValidPhone.create();
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_PHONE),
                requestBodyForLoginByValidPhone.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(200);
        softly.assertThat(response.jsonPath().getInt("data.lifetime_remaining")).isEqualTo(120);
        softly.assertAll();
    }

    @Test
    @DisplayName("Login by valid email and password")
    public void testLoginByValidEmailAndPassword() {
        RequestBodyForLoginByValidEmailAndPassword requestBody =
                RequestBodyForLoginByValidEmailAndPassword.create();
        Response response = Steps.runLoginTest(Endpoints.getLoginUrl(Endpoints.LOGIN_BY_EMAIL),
                requestBody.getBody());

        softly.assertThat(response.statusCode()).isEqualTo(200);
        String emailFromResponse = response.jsonPath().getString("data.email");
        softly.assertThat(emailFromResponse).isEqualTo(requestBody.getEmail());
        softly.assertAll();
    }

    @AfterEach
    public void tearsDown(TestInfo testInfo) {
        logger.info("Test completed: {}", testInfo.getDisplayName());

    }
}

