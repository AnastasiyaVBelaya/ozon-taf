package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class PartialProductName {
    List<String> partialProductNames;

    public PartialProductName() {
        this.partialProductNames =
                TestDataUtils.loadTestDataFromJson("partialProductNames.json", new TypeReference<List<String>>() {});
    }

    public String getRandomValue() {
        return TestDataUtils.getRandomFromList(partialProductNames);
    }
}
