package by.it_academy.belaya.utils;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class Steps {

    private static Map<String, String> headers;
    private static Map<String, String> cookies;

    public static void loadHeadersAndCookies() throws IOException {
        headers = BrowserConfigLoader.loadHeaders();
        cookies = BrowserConfigLoader.loadCookies();
    }

    public static void runSearchTest(String searchEndpoint, String expected, Function<String, String> parser) {
        String responseBody = given()
                .headers(headers)
                .cookies(cookies)
                .when()
                .get(Endpoints.getSearchUrl(searchEndpoint))
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        String actualText = parser.apply(responseBody);
        assertThat(actualText, containsString(expected));
    }
}
