package tracker;

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

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
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
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker()).init();
    }

    /**
     * метод останавливает работу цикла в init
     */
    public void stop() {
        this.run = false;
    }
}
