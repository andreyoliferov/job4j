package io;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @autor aoliferov
 * @since 09.01.2019
 */
public class ServiceIOTest {

    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {"13", false},
                {"f", false},
                {"g3", false},
                {"g4", false},
                {"g4 3 f", false},
                {"14", true},
                {"8", true},
                {"8 d", true},
                {"13 rf 8 d", true},
        };
    }
    @Test(dataProvider = "data")
    public void whenInThenResult(String in, boolean except) {
        ServiceIO io = new ServiceIO();
        boolean result = io.isEvenNumber(new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)));
        assertThat(result, is(except));
    }

}
