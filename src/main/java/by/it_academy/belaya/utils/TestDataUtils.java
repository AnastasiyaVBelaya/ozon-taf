package by.it_academy.belaya.utils;

import by.it_academy.belaya.enums.Countries;
import by.it_academy.belaya.exceptions.ConfigFileNotFoundException;
import by.it_academy.belaya.exceptions.ConfigFileReadingException;
import by.it_academy.belaya.testdata.CountryCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;


public class TestDataUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random random = new Random();
    private static final Logger logger = LogManager.getLogger(TestDataUtils.class);
    private static final Faker faker = new Faker();


    public static <T> T loadTestDataFromJson(String fileName, TypeReference<T> typeReference) {
        try (InputStream inputStream = TestDataUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                logger.error("Configuration file not found: {}", fileName);
                throw new ConfigFileNotFoundException(fileName);
            }

            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            logger.error("Error reading configuration file: {}", fileName, e);
            throw new ConfigFileReadingException(fileName, e);
        }
    }

    public static String getRandomFromList(List<String> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }
        String value = list.get(random.nextInt(list.size()));
        logger.info("Value is {}", value);
        return value;
    }

    public static String generateSearchQueryWithRandomSpaces(String baseQuery) {
        int leadingSpacesCount = random.nextInt(10) + 1;
        int trailingSpacesCount = random.nextInt(10) + 1;
        String result = " ".repeat(leadingSpacesCount) + baseQuery + " ".repeat(trailingSpacesCount);
        logger.info("Search query with random spaces is {}", result);
        return result;
    }

    public static String generatePhoneNumber(Countries country) {
        String code = CountryCode.getRandomCodeForCountry(country);
        int firstDigit = random.nextInt(9) + 1;
        StringBuilder remainingDigits = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            remainingDigits.append(random.nextInt(10));
        }
        String phoneNumber = code + firstDigit + remainingDigits.toString();
        logger.info("Generated phone number is {}", phoneNumber);

        return phoneNumber;
    }

    public static String generateRandomEmail() {
        String email = faker.internet().emailAddress();
        logger.info("Generated email: {}", email);
        return email;
    }

    public static String generateRandomPassword() {
        String password = faker.internet().password(8, 16);
        logger.info("Generated password: {}", password);
        return password;
    }

    public static String formatPhoneNumberWithHyphens(String phoneNumber) {
        String result = phoneNumber.replaceAll("(\\d{2})(\\d{3})(\\d{2})(\\d{2})", "$1-$2-$3-$4");
        logger.info("Formatted phone number: {}", result);
        return result;
    }

    public static String formatPhoneNumberWithBlanks(String phoneNumber) {
        String result = phoneNumber.replaceAll("(\\d{2})(\\d{3})(\\d{2})(\\d{2})", "$1 $2 $3 $4");
        logger.info("Formatted phone number: {}", result);
        return result;
    }
}
