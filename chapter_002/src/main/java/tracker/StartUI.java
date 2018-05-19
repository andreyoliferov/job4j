package tracker;

/**
 * @autor Андрей
 * @since 19.05.2018
 */
public class StartUI {
    /** Константа меню для добавления новой заявки.*/
    private static final String ADD = "0";

    /**Константа меню для добавления новой заявки.*/
    private static final String SHOW_ALL = "1";

    /**Константа меню для добавления новой заявки.*/
    private static final String EDIT = "2";

    /**Константа меню для добавления новой заявки.*/
    private static final String DELETE = "3";

    /**Константа меню для добавления новой заявки.*/
    private static final String FIND_OF_ID = "4";

    /**Константа меню для добавления новой заявки.*/
    private static final String FIND_OF_NAME = "5";

    /**Константа для выхода из цикла.*/
    private static final String EXIT = "6";

    /**Получение данных от пользователя.*/
    private final Input input;

    /**Хранилище заявок*/
    private final Tracker tracker;

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Введите пункт меню : ");

            switch (answer) {
                case ADD : this.createItem();
                    break;
                case SHOW_ALL : this.showAllItem();
                    break;
                case EDIT : this.editItem();
                    break;
                case DELETE : this.deleteItem();
                    break;
                case FIND_OF_ID : this.findByIdItem();
                    break;
                case FIND_OF_NAME : this.findByNameItem();
                    break;
                case EXIT : exit = true;
                    break;
                default:
                    System.out.println("Некорректный пункт меню\n");
                    break;
            }
        }
    }

    /**
     * Метод реализует добавленяи новый заявки в хранилище.
     */
    private void createItem() {
        System.out.println("------------ Добавление новой заявки --------------");
        String name = this.input.ask("Введите имя заявки :");
        String desc = this.input.ask("Введите заявку :");
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println("------- Создана новая заявка с Id : " + item.getId() + "--------");
    }

    /**
     * Метод отображает все заявки
     */
    private void showAllItem() {
        Item[] finded = this.tracker.findAll();
        for (int i = 0; i < finded.length; i++) {
            System.out.println("--------Заявка №" + (i + 1) + "----------");
            System.out.println(finded[i].getName());
            System.out.println(finded[i].getDesc());
            System.out.println(finded[i].getId());
            System.out.println(finded[i].getCreated());
        }
    }

    /**
     * Метод редактирует заявку
     */
    private void editItem() {
        System.out.println("------------ Редактирование заявки--------------");
        String id = this.input.ask("Введите новое имя заявки :");
        String newName = this.input.ask("Введите новое имя заявки :");
        String newDesc = this.input.ask("Введите новый текст заявки :");
        Item item = new Item(newName, newDesc);
        this.tracker.replace(id, item);
        System.out.println("------------ Заявка изменена --------------");
    }

    /**
     * Метод удалет заявку
     */
    private void deleteItem() {
        System.out.println("------------ Удаление заявки --------------");
        String id = this.input.ask("Введите ID заявки");
        this.tracker.delete(id);
        System.out.println("------------ Заявка удалена --------------");
    }

    /**
     * Метод находит заявку по имени
     */
    private void findByNameItem() {
        System.out.println("------------ Найти заявку по имени --------------");
        String name = this.input.ask("Введите имя заявки");
        Item finded = this.tracker.findByName(name);
        System.out.println("--------Заявка: " + name);
        System.out.println("ID: " + finded.getId());
        System.out.println("Дата создания: " + finded.getCreated());
        System.out.println(finded.getDesc());
    }

    /**
     * метод находит заявку по ID
     */
    private void findByIdItem() {
        System.out.println("------------ Найти заявку по ID --------------");
        String id = this.input.ask("Введите ID заявки");
        Item finded = this.tracker.findById(id);
        System.out.println("--------Заявка: " + id);
        System.out.println("Имя: " + finded.getName());
        System.out.println("Дата создания: " + finded.getCreated());
        System.out.println(finded.getDesc());
    }

    /**
     * Показать меню
     */
    private void showMenu() {
        String[] menu = {"0. Добавить новую заявку",
                "1. Показать все заявки",
                "2. Редактировать заявку",
                "3. Удалить заявку",
                "4. Найти заявку по ID",
                "5. Найти заявку по имени",
                "6. Выйти"};
        for (String item : menu) {
            System.out.println(item);
        }
    }

    /**
     * Запуск программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
