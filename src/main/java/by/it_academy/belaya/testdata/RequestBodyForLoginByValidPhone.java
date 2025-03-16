package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class RequestBodyForLoginByValidPhone extends RequestBodyForLoginByPhone {

    public static RequestBodyForLoginByValidPhone create() {
        List<String> phones = loadPhonesFromJson();
        String phone = TestDataUtils.getRandomFromList(phones);
        return new RequestBodyForLoginByValidPhone(phone);
    }

    private RequestBodyForLoginByValidPhone(String phone) {
        super(phone);
    }

    private static List<String> loadPhonesFromJson() {
        return TestDataUtils.loadTestDataFromJson("phoneNumbersForLoginWithAPI.json",
                new TypeReference<List<String>>() {});
    }
}
