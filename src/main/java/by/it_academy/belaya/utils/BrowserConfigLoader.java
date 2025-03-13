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
    private static final String HEADERS_FOR_LOGIN_FILE = "/headersForLogin.json";

    public static ChromeOptions loadChromeOptions() {
        Properties properties = loadProperties(CHROME_OPTIONS_CONFIG_FILE);
        ChromeOptions options = new ChromeOptions();
        properties.forEach((key, value) -> options.addArguments(key + "=" + value));
        return options;
    }

    public static Map<String, String> loadHeadersForSearch() throws IOException {
        return loadJsonFile(HEADERS_AND_COOKIES_FOR_SEARCH_FILE).get("headers");
    }

    public static Map<String, String> loadCookiesForSearch() throws IOException {
        return loadJsonFile(HEADERS_AND_COOKIES_FOR_SEARCH_FILE).get("cookies");
    }

    public static Map<String, String> loadHeadersForLogin() throws IOException {
        return loadJsonFile(HEADERS_FOR_LOGIN_FILE).get("headers");
    }

    private static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = BrowserConfigLoader.class.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                logger.error("Configuration file not found: {}", fileName);
                throw new ConfigFileNotFoundException(fileName);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Error reading configuration file: {}", fileName, e);
            throw new ConfigFileReadingException(e, fileName);
        }
        return properties;
    }

    private static Map<String, Map<String, String>> loadJsonFile(String fileName) throws IOException {
        try (InputStream inputStream = BrowserConfigLoader.class.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                logger.error("Configuration file not found: {}", fileName);
                throw new ConfigFileNotFoundException(fileName);
            }
            return objectMapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            logger.error("Error reading configuration file: {}", fileName, e);
            throw new ConfigFileReadingException(e, fileName);
        }
    }
}
