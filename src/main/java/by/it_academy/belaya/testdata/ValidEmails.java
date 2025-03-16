package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class ValidEmails {
    List<String> validEmails;

    public ValidEmails() {
        this.validEmails =
                TestDataUtils.loadTestDataFromJson("validEmailsForLoginWIthUI.json", new TypeReference<List<String>>() {
                });
    }

    public String getRandomValue() {
        return TestDataUtils.getRandomFromList(validEmails);
    }
}
