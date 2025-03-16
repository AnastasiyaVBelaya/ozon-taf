package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public class RequestBodyForSearch {
    private final String requestBody;
    private final String query;

    public RequestBodyForSearch(String query) {
        Map<String, Object> template = loadTemplateFromJson();
        this.query = query;
        this.requestBody = createRequestBody(template);
    }

    private Map<String, Object> loadTemplateFromJson() {
        return TestDataUtils.loadTestDataFromJson("requestBodySearchTemplate.json",
                new TypeReference<Map<String, Object>>() {
                });
    }

    private String createRequestBody(Map<String, Object> template) {
        template.put("query", this.query);
        return TestDataUtils.toJson(template);
    }

    public String getBody() {
        return this.requestBody;
    }

    public String getQuery() {
        return this.query;

    }
}
