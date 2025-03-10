package by.it_academy.belaya.utils;

import by.it_academy.belaya.exceptions.ConfigFileNotFoundException;
import by.it_academy.belaya.exceptions.ConfigFileReadingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ChromeOptionsConfig {

    private static final Logger logger = LogManager.getLogger(ChromeOptionsConfig.class);

    private static final String CHROME_OPTIONS_CONFIG_FILE = "/chrome_options.properties";

    public static ChromeOptions loadChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Properties properties = new Properties();

        try (InputStream inputStream = ChromeOptionsConfig.class.getResourceAsStream(CHROME_OPTIONS_CONFIG_FILE)) {
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
}
