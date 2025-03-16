package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class RequestBodyForLoginByValidEmailAndPassword extends RequestBodyForLoginByEmail {

    public static RequestBodyForLoginByValidEmailAndPassword create() {
        List<String> emails = loadEmailsFromJson();
        String email = TestDataUtils.getRandomFromList(emails);
        String password = "123456";
        return new RequestBodyForLoginByValidEmailAndPassword(email, password);
    }

    private RequestBodyForLoginByValidEmailAndPassword(String email, String password) {
        super(email, password);
    }

    private static List<String> loadEmailsFromJson() {
        return TestDataUtils.loadTestDataFromJson("emailsForLoginWithAPI.json",
                new TypeReference<List<String>>() {
                });
    }
}
