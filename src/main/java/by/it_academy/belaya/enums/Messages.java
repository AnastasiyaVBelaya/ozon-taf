package by.it_academy.belaya.enums;

public enum Messages {
    NO_RESULTS("Простите, по вашему запросу товаров сейчас нет."),
    PAGE_NOT_FOUND("Такой страницы не существует"),
    NO_ACCOUNT_WITH_SUCH_EMAIL("We can't find the account with this email. Try entering a different one or logging in with your phone number."),
    BLANK_EMAIL("Fill in your e-mail"),
    INCORRECT_MAIL_FORMAT("Incorrect mail format"),
    INCORRECT_PHONE_FORMAT("Incorrect phone format");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
