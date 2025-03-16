package by.it_academy.belaya.exceptions;

public class ConfigFileNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Configuration file not found: ";

    public ConfigFileNotFoundException(String fileName) {
        super(DEFAULT_MESSAGE + fileName);
    }
}
