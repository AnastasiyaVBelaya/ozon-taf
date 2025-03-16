package by.it_academy.belaya.utils;

import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;


import static io.restassured.RestAssured.given;

public class Steps {

    private static Map<String, String> headers;

    public static void loadHeaders() throws IOException {
        headers= BrowserConfigLoader.loadHeaders();
    }

    public static Response runSearchTest(String searchEndpoint, String body) {
        return given()
                .headers(headers)
                .body(body)
                .log().all()
                .when()
                .post(searchEndpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response runLoginTest(String loginEndpoint, String body) {
        return given()
                .headers(headers)
                .body(body)
                .log().all()
                .when()
                .post(loginEndpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
