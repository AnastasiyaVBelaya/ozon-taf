package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class QueryWithSpacesInTheMiddle {
    List<String> queryWithSpacesInTheMiddle;

    public QueryWithSpacesInTheMiddle() {
        this.queryWithSpacesInTheMiddle =
                TestDataUtils.loadTestDataFromJson("queryWithSpacesInTheMiddle.json",new TypeReference<List<String>>() {});
    }

    public String getRandomValue() {
        return TestDataUtils.getRandomFromList(queryWithSpacesInTheMiddle);
    }
}
