package tracker;

/**
 * Создать заявку
 */
class CreateItem  implements UserAction {

    @Override
    public int key() {
        return 0;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("------------ Добавление новой заявки --------------");
        String name = input.ask("Введите имя заявки :");
        String desc = input.ask("Введите заявку :");
        Item item = new Item(name, desc);
        tracker.add(item);
        System.out.println("------- Создана новая заявка с Id : " + item.getId() + "--------");
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Добавить новую заявку");
    }
}

/**
 * @autor Андрей Олиферов
 * @since 26.05.2018
 */
public class MenuTracker {

    boolean run = true;

    /**Получение данных от пользователя.*/
    private Input input;

    /**Хранилище заявок*/
    private Tracker tracker;

    private UserAction[] actions = new UserAction[15];

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void fillActions() {
        actions[0] = new CreateItem();
        actions[1] = new MenuTracker.ShowAllItem();
        actions[2] = this.new EditItem();
        actions[3] = new DeleteItem();
        actions[4] = new FindByNameItem();
        actions[5] = new FindByIdItem();
        actions[6] = new Out();
    }

    public void select(int key) {
        this.actions[key].execute(this.input, this.tracker);
    }

    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * показать все заявки
     */
    public static class ShowAllItem implements UserAction {

        @Override
        public int key() {
            return 1;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            Item[] finded = tracker.findAll();
            for (int i = 0; i < finded.length; i++) {
                System.out.println("--------Заявка №" + (i + 1) + "----------");
                System.out.println(finded[i].getName());
                System.out.println(finded[i].getDesc());
                System.out.println(finded[i].getId());
                System.out.println(finded[i].getCreated());
            }
        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Показать все заявки");
        }
    }

    /**
     * отредактировать заявку
     */
    public class EditItem implements UserAction {

        @Override
        public int key() {
            return 2;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Редактирование заявки--------------");
            String id = input.ask("Введите id заявки :");
            String newName = input.ask("Введите новое имя заявки :");
            String newDesc = input.ask("Введите новый текст заявки :");
            Item item = new Item(newName, newDesc);
            tracker.replace(id, item);
            System.out.println("------------ Заявка изменена --------------");
        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Редактировать заявку");
        }
    }

    /**
     * удалить заявку
     */
    public class DeleteItem implements UserAction {

        @Override
        public int key() {
            return 3;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Удаление заявки --------------");
            String id = input.ask("Введите ID заявки");
            tracker.delete(id);
            System.out.println("------------ Заявка удалена --------------");
        }

        @Override
        public String info() {
            return  String.format("%s. %s", this.key(), "Удалить заявку");
        }
    }

    /**
     * найти по имени
     */
    public class FindByNameItem implements UserAction {

        @Override
        public int key() {
            return 4;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Найти заявку по имени --------------");
            String name = input.ask("Введите имя заявки");
            Item[] finded = tracker.findByName(name);
            for (Item item : finded) {
                System.out.println("--------Заявка: " + name);
                System.out.println("ID: " + item.getId());
                System.out.println("Дата создания: " + item.getCreated());
                System.out.println(item.getDesc());
            }
        }

        @Override
        public String info() {
            return  String.format("%s. %s", this.key(), "Найти заявку по имени");
        }
    }

    /**
     * найти по ID
     */
    public class FindByIdItem implements UserAction {

        @Override
        public int key() {
            return 5;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Найти заявку по ID --------------");
            String id = input.ask("Введите ID заявки");
            Item finded = tracker.findById(id);
            System.out.println("--------Заявка: " + id);
            System.out.println("Имя: " + finded.getName());
            System.out.println("Дата создания: " + finded.getCreated());
            System.out.println(finded.getDesc());
        }

        @Override
        public String info() {
            return  String.format("%s. %s", this.key(), "Найти заявку по ID");
        }
    }

    /**
     * Выйти
     */
    public class Out implements UserAction {

        @Override
        public int key() {
            return 6;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            run = false;
        }

        @Override
        public String info() {
            return  String.format("%s. %s", this.key(), "Выйти");
        }
    }
}
