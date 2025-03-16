package by.it_academy.belaya.utils;

public class Endpoints {

    public static final String BASE_LOGIN_URL = "https://gate.21vek.by/";

    public static final String LOGIN_BY_PHONE = "account/users/login/send-confirmation";
    public static final String LOGIN_BY_EMAIL = "sso/login-by-email";
    public static final String SEARCH_URL = "https://gate.21vek.by/search-composer/api/v2/products";

    public static String getSearchUrl() {
        return SEARCH_URL;
    }

    public static String getLoginUrl(String path) {
        return String.format("%s%s", BASE_LOGIN_URL, path);
    }
}

