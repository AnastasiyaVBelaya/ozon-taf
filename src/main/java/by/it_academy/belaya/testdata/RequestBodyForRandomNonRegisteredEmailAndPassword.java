package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;

public class RequestBodyForRandomNonRegisteredEmailAndPassword extends RequestBodyForLoginByEmail {

    public static RequestBodyForRandomNonRegisteredEmailAndPassword create() {
        String email = generateRandomEmail();
        String password = generateRandomPassword();
        return new RequestBodyForRandomNonRegisteredEmailAndPassword(email, password);
    }

    private RequestBodyForRandomNonRegisteredEmailAndPassword(String email, String password) {
        super(email, password);
    }

    private static String generateRandomEmail() {
        return TestDataUtils.generateRandomEmail();
    }

    private static String generateRandomPassword() {
        return TestDataUtils.generateRandomPassword();
    }
}
