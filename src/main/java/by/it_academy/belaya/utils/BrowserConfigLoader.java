package by.it_academy.belaya.utils;

import by.it_academy.belaya.exceptions.ConfigFileNotFoundException;
import by.it_academy.belaya.exceptions.ConfigFileReadingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class BrowserConfigLoader {

    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String CHROME_OPTIONS_CONFIG_FILE = "/chrome_options.properties";
    private static final String HEADERS_AND_COOKIES_FOR_SEARCH_FILE = "/headersAndCookiesForSearch.json";
    private static final String HEADERS_AND_COOKIES_FOR_LOGIN_FILE = "/headersAndCookiesForLogin.json";

    public static ChromeOptions loadChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Properties properties = new Properties();

        try (InputStream inputStream = BrowserConfigLoader.class.getResourceAsStream(CHROME_OPTIONS_CONFIG_FILE)) {
            if (inputStream == null) {
                logger.error("Configuration file not found: " + CHROME_OPTIONS_CONFIG_FILE);
                throw new ConfigFileNotFoundException(CHROME_OPTIONS_CONFIG_FILE);
            }
            properties.load(inputStream);
            properties.forEach((key, value) -> options.addArguments(key + "=" + value));

        } catch (IOException e) {
            logger.error("Error reading configuration file: " + CHROME_OPTIONS_CONFIG_FILE, e);
            throw new ConfigFileReadingException(e, CHROME_OPTIONS_CONFIG_FILE);
        }
        return options;
    }

    public static Map<String, String> loadHeaders() throws IOException {
        return loadHeadersAndCookiesForSearch().get("headers");
    }

    public static Map<String, String> loadCookies() throws IOException {
        return loadHeadersAndCookiesForSearch().get("cookies");
    }

    public static Map<String, String> loadHeadersForLogin() throws IOException {
        return loadHeadersAndCookiesForSearch().get("headers");
    }

    public static Map<String, String> loadCookiesForLogin() throws IOException {
        return loadHeadersAndCookiesForSearch().get("cookies");
    }

    private static Map<String, Map<String, String>> loadHeadersAndCookiesForSearch() throws IOException {
        try (InputStream inputStream = BrowserConfigLoader.class.getResourceAsStream(HEADERS_AND_COOKIES_FOR_SEARCH_FILE)) {
            if (inputStream == null) {
                logger.error("Configuration file not found: {}", HEADERS_AND_COOKIES_FOR_SEARCH_FILE);
                throw new ConfigFileNotFoundException(HEADERS_AND_COOKIES_FOR_SEARCH_FILE);
            }

            return objectMapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            logger.error("Error reading configuration file: {}", HEADERS_AND_COOKIES_FOR_SEARCH_FILE, e);
            throw new ConfigFileReadingException(e, HEADERS_AND_COOKIES_FOR_SEARCH_FILE);
        }
    }

    private static Map<String, Map<String, String>> loadHeadersAndCookiesForLogin() throws IOException {
        try (InputStream inputStream = BrowserConfigLoader.class.getResourceAsStream(HEADERS_AND_COOKIES_FOR_LOGIN_FILE)) {
            if (inputStream == null) {
                logger.error("Configuration file not found: {}", HEADERS_AND_COOKIES_FOR_LOGIN_FILE);
                throw new ConfigFileNotFoundException(HEADERS_AND_COOKIES_FOR_LOGIN_FILE);
            }

            return objectMapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            logger.error("Error reading configuration file: {}", HEADERS_AND_COOKIES_FOR_LOGIN_FILE, e);
            throw new ConfigFileReadingException(e, HEADERS_AND_COOKIES_FOR_LOGIN_FILE);
        }
    }
}
