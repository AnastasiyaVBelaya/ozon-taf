package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class RequestBodyForLoginWithValidEmailAndRandomPassword extends RequestBodyForLoginByEmail {

    public static RequestBodyForLoginWithValidEmailAndRandomPassword create() {
        List<String> emails = loadEmailsFromJson();
        String email = TestDataUtils.getRandomFromList(emails);
        String password = generateRandomPassword();
        return new RequestBodyForLoginWithValidEmailAndRandomPassword(email, password);
    }

    private RequestBodyForLoginWithValidEmailAndRandomPassword(String email, String password) {
        super(email, password);
    }

    private static List<String> loadEmailsFromJson() {
        return TestDataUtils.loadTestDataFromJson("emailsForLoginWithAPI.json",
                new TypeReference<List<String>>() {
                });
    }

    private static String generateRandomPassword() {
        return TestDataUtils.generateRandomPassword();
    }
}
