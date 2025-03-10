package by.it_academy.belaya.exceptions;

public class WebDriverCreationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Failed to create the driver";

    public WebDriverCreationException() {
        super(DEFAULT_MESSAGE);
    }

    public WebDriverCreationException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public WebDriverCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
