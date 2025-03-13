package by.it_academy.belaya.exceptions;

public class ConfigFileReadingException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Error reading configuration file: ";

    public ConfigFileReadingException(Throwable cause, String fileName) {
        super(DEFAULT_MESSAGE + fileName, cause);
    }

    public ConfigFileReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
