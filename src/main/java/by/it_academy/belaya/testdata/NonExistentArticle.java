package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class NonExistentArticle {
    List<String> nonExistentArticles;

    public NonExistentArticle() {
        this.nonExistentArticles =
                TestDataUtils.loadTestDataFromJson("nonExistentArticles.json", new TypeReference<List<String>>() {});
    }

    public String getRandomValue() {
        return TestDataUtils.getRandomFromList(nonExistentArticles);
    }
}
