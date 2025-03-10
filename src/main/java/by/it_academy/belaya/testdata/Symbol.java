package by.it_academy.belaya.testdata;

import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class Symbol {
    List<String> symbols;

    public Symbol() {
        this.symbols =
                TestDataUtils.loadTestDataFromJson("symbols.json", new TypeReference<List<String>>() {});
    }

    public String getRandomValue() {
        return TestDataUtils.getRandomFromList(symbols);
    }
}
