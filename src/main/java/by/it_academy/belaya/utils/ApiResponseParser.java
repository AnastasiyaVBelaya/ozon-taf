package by.it_academy.belaya.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ApiResponseParser {

    private static final Logger logger = LogManager.getLogger();
    private static final String ARTICLE_BUTTON = "button[data-widget=webDetailSKU] .tsBodyControl400Small";
    private static final String FULL_TEXT_RESULT_HEADER = "div[data-widget='fulltextResultsHeader'] .faa1_35 strong";
    private static final String FIRST_PRODUCT_CARD = "div.bq017-a.bq017-a4.bq017-a6.jl9_25 span.tsBody500Medium";
    private static final String PAGE_NOT_FOUND_MESSAGE = "div[data-widget='error'] h2.c4";
    private static final String NO_RESULTS_MESSAGE = "div .aa2g_35";
    private static final String ERROR_HAPPENED_MESSAGE = "div.aa2g_35";

    public static String getElementFromResponse(String responseBody, String element) {
        logger.info("Parsing response to find element: {}", element);
        Document doc = Jsoup.parse(responseBody);
        Element selectedElement = doc.select(element).first();
        if (selectedElement != null) {
            logger.info("Element found: {}", selectedElement.text());
            return selectedElement.text();
        } else {
            logger.warn("Element not found for selector: {}", element);
            return "";
        }
    }

    public static String getArticleFromResponse(String responseBody) {
        return getElementFromResponse(responseBody, ARTICLE_BUTTON);
    }

    public static String getTextFromResultHeader(String responseBody) {
        return getElementFromResponse(responseBody, FULL_TEXT_RESULT_HEADER);
    }

    public static String getTextFromFirstProductCard(String responseBody) {
        return getElementFromResponse(responseBody, FIRST_PRODUCT_CARD);
    }

    public static String getTextFromPageNotFoundMessage(String responseBody) {
        return getElementFromResponse(responseBody, PAGE_NOT_FOUND_MESSAGE);
    }

    public static String getTextFromNoResultsMessage(String responseBody) {
        return getElementFromResponse(responseBody, NO_RESULTS_MESSAGE);
    }

    public static String getTextFromErrorHappenedMessage(String responseBody) {
        return getElementFromResponse(responseBody, ERROR_HAPPENED_MESSAGE);
    }
}
