package generator;

import java.util.Map;

/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public interface IGenerator {

    String generate(String data, Map<String, String> catalog) throws GenerateException;

}
