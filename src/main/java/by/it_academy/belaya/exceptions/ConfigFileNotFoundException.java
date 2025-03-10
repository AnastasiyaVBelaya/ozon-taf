package by.it_academy.belaya.exceptions;

public class ConfigFileNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Configuration file not found: ";

    public ConfigFileNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ConfigFileNotFoundException(String fileName) {
        super(DEFAULT_MESSAGE + fileName);
    }

    public ConfigFileNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public ConfigFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
