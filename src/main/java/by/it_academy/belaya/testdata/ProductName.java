package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class ProductName {
    private final List<String> productNames;

    public ProductName() {
        this.productNames =
                TestDataUtils.loadTestDataFromJson("productNames.json", new TypeReference<List<String>>() {});
    }

    public String getRandomValue() {
        return TestDataUtils.getRandomFromList(productNames);
    }
}
