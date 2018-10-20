package trackersql;

import tracker.*;

/**
 * @autor aoliferov
 * @since 20.10.2018
 */
public class StartSQL extends StartUI {
    /**
     * Конструтор инициализирующий поля.
     *
     * @param input   ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartSQL(Input input, ITracker tracker) {
        super(input, tracker);
    }

    /**
     * Запуск программы.
     * @param args
     */
    public static void main(String[] args) throws Exception {
        try (TrackerSQL tracker = new TrackerSQL()) {
            new StartUI(new ValidateInput(new ConsoleInput()), tracker).init();
        }
    }
}
