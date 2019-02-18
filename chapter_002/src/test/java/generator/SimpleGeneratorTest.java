package generator;

import org.testng.annotations.Test;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public class SimpleGeneratorTest {

    private IGenerator generator = new SimpleGenerator();

    @Test
    public void whenGanerateReturnStr() {
        String data = "Hello, ${username}!";
        Map<String, String> catalog = Map.of("username", "Andrey");
        String result = generator.generate(data, catalog);
        assertThat(result, is("Hello, Andrey!"));
    }

    @Test(expectedExceptions = GenerateException.class)
    public void whenGanerateReturnExceptionData() {
        String data = "${name}, ${username}!";
        Map<String, String> catalog = Map.of("username", "Andrey");
        generator.generate(data, catalog);
    }

    @Test(expectedExceptions = GenerateException.class)
    public void whenGanerateReturnExceptionCatalog() {
        String data = "Hello, ${username}!";
        Map<String, String> catalog = Map.of("username", "Andrey", "key", "value");
        generator.generate(data, catalog);
    }

}