package by.it_academy.belaya.exceptions;

public class WebDriverCreationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Failed to create the driver";

    public WebDriverCreationException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
