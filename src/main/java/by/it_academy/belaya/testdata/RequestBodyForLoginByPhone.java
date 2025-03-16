package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public abstract class RequestBodyForLoginByPhone {
    protected final String requestBody;
    protected final String phone;

    public RequestBodyForLoginByPhone(String phone) {
        Map<String, String> template = loadTemplateFromJson();
        this.phone = phone;
        this.requestBody = createRequestBody(template);
    }

    private Map<String, String> loadTemplateFromJson() {
        return TestDataUtils.loadTestDataFromJson("requestBodyLoginByPhoneTemplate.json",
                new TypeReference<Map<String, String>>() {});
    }

    private String createRequestBody(Map<String, String> template) {
        template.put("phone", this.phone);
        return TestDataUtils.toJson(template);
    }

    public String getBody() {
        return this.requestBody;
    }

    public String getPhone() {
        return this.phone;
    }
}
