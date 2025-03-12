package by.it_academy.belaya.utils;

public class Endpoints {

    public static final String BASE_URL = "https://ozon.by/";
    public static final String VALID_ARTICLE = "1737637528";
    public static final String SEARCH_BY_VALID_ARTICLE = "product/naruchnye-chasy-casio-f-91ws-4-1737637528/";
    public static final String VALID_NAME_FOR_HEADER = "телефон";
    public static final String SEARCH_BY_VALID_NAME_FOR_HEADER = "category/smartfony-15502/?category_was_predicted=true&deny_category_prediction=true&from_global=true&text=телефон";
    public static final String VALID_NAME_FOR_CARD = "телевизор";
    public static final String SEARCH_BY_VALID_NAME_FOR_CARD = "category/televizory-15528/?category_was_predicted=true&deny_category_prediction=true&from_global=true&text=телевизор";
    public static final String SEARCH_BY_INVALID_ARTICLE = "product/bryuki-1234567890/?sh=csCh3q0t0A";
    public static final String SEARCH_BY_SYMBOLS = "search/?text=/////////////////&from_global=true";
    public static final String SEARCH_BY_EMPTY_QUERY = "search/?from_global=true&text=";
    public static final String SEARCH_BY_SPACES = "search/?from_global=true&text=     ";
    public static final String SEARCH_BY_TOO_LONG_QUERY = "search/?from_global=true&text="+"9".repeat(1000);

    public static String getSearchUrl(String search) {
        return BASE_URL + search;
    }
}

