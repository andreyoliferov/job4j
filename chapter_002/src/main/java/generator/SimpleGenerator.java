package generator;

import java.util.Map;

/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public class SimpleGenerator implements IGenerator {

    @Override
    public String generate(String data, Map<String, String> catalog) throws GenerateException {
        for (Map.Entry<String, String> e : catalog.entrySet()) {
            String toReplase = String.format("\\$\\{%s}", e.getKey());
            if (!data.matches(String.format(".*%s.*", toReplase))) {
                throw new GenerateException("В справочнике избыточные данные!");
            }
            data = data.replaceAll(toReplase, e.getValue());
        }
        if (data.matches(".*\\$\\{.+}.*")) {
            throw new GenerateException("В справочнике не хватает данных для замены!");
        }
        return data;
    }
}
