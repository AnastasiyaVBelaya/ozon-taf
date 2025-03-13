package by.it_academy.belaya.utils;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import static io.restassured.RestAssured.given;

public class Steps {

    private static Map<String, String> searchHeaders;
    private static Map<String, String> searchCookies;
    private static Map<String, String> loginHeaders;

    public static void loadHeadersAndCookiesForSearch() throws IOException {
        searchHeaders = BrowserConfigLoader.loadHeadersForSearch();
        searchCookies = BrowserConfigLoader.loadCookiesForSearch();
    }

    public static void loadHeadersForLogin() throws IOException {
        loginHeaders = BrowserConfigLoader.loadHeadersForLogin();
    }

    public static Response runSearchTest(String searchEndpoint) {
        return given()
                .headers(searchHeaders)
                .cookies(searchCookies)
                .when()
                .get(Endpoints.getSearchUrl(searchEndpoint));
    }

    public static Response runLoginTest(String loginEndpoint) {
        return given()
                .headers(loginHeaders)
                .when()
                .post(Endpoints.getSearchUrl(loginEndpoint));
    }
}
