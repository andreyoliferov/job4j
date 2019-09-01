package tracker.sql;

import tracker.ConsoleInput;
import tracker.StartUI;
import tracker.ValidateInput;

/**
 * @autor aoliferov
 * @since 01.09.2019
 */
public class StartSQL {

    /**
     * Запуск программы.
     * @param args
     */
    public static void main(String[] args) throws Exception {
        try (TrackerSQL tracker = new TrackerSQL()) {
            new StartUI(new ValidateInput(new ConsoleInput()), tracker, System.out::println).init();
        }
    }
}
