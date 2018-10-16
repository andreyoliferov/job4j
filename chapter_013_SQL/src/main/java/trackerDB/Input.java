package trackerDB;

import java.util.List;

/**
 * @autor Андрей
 * @since 19.05.2018
 */
public interface Input {

    String ask(String question);
    int ask(String question, List<Integer> range);
}
