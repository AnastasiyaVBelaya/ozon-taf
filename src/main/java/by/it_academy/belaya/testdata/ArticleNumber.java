package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class ArticleNumber {
    private final List<String> articleNumbers;

    public ArticleNumber() {
        this.articleNumbers =
                TestDataUtils.loadTestDataFromJson("articleNumbers.json", new TypeReference<List<String>>() {});
    }

    public String getRandomValue() {
        return TestDataUtils.getRandomFromList(articleNumbers);
    }
}
