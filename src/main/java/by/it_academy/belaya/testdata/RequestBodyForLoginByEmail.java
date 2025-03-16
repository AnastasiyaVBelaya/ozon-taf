package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public abstract class RequestBodyForLoginByEmail {
    protected final String requestBody;
    protected final String email;
    protected final String password;

    public RequestBodyForLoginByEmail(String email, String password) {
        Map<String, String> template = loadTemplateFromJson();
        this.email = email;
        this.password = password;
        this.requestBody = createRequestBody(template);
    }

    private Map<String, String> loadTemplateFromJson() {
        return TestDataUtils.loadTestDataFromJson("requestBodyLoginByEmailTemplate.json",
                new TypeReference<Map<String, String>>() {});
    }

    private String createRequestBody(Map<String, String> template) {
        template.put("email", this.email);
        template.put("password", this.password);
        return TestDataUtils.toJson(template);
    }

    public String getBody() {
        return this.requestBody;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
