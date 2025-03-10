package by.it_academy.belaya.testdata;

import by.it_academy.belaya.enums.Countries;
import by.it_academy.belaya.utils.TestDataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class CountryCode {
    private static final Map<String, List<String>> countryCodes;

    static {
        countryCodes = TestDataUtils.loadTestDataFromJson("countryMobileOperatorsCodes.json", new TypeReference<Map<String, List<String>>>() {
        });
    }

    public static String getRandomCodeForCountry(Countries country) {
        List<String> codes = countryCodes.get(country.name());
        if (codes == null || codes.isEmpty()) {
            throw new IllegalArgumentException("No country codes found for country: " + country);
        }
        return TestDataUtils.getRandomFromList(codes);
    }
}
