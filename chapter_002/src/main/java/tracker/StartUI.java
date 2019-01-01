package tracker;

import java.util.function.Consumer;

/**
 * @autor Андрей
 * @since 19.05.2018
 */
public class StartUI {

    /**Получение данных от пользователя.*/
    private final Input input;

    /**Хранилище заявок*/
    private final ITracker tracker;

    private boolean run = true;
    private final Consumer<String> output;

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, ITracker tracker, Consumer<String> output) {
        this.input = input;
        this.tracker = tracker;
        this.output = output;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker, this.output);
        menu.fillActions(this);
        while (this.run) {
            menu.show();
            menu.select(this.input.ask("Введите пункт меню : ", menu.range()));
        }
    }

    /**
     * Запуск программы.
     * @param args
     */
    public static void main(String[] args) throws Exception {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker(), System.out::println).init();
    }

    /**
     * метод останавливает работу цикла в init
     */
    public void stop() {
        this.run = false;
    }
}
