package tracker;

import java.util.Arrays;

/**
 * Создать заявку
 */
class CreateItem extends BaseAction {
    public CreateItem() {
        super(0, "Добавить новую заявку");
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
}

/**
 * @autor Андрей Олиферов
 * @since 26.05.2018
 */
public class MenuTracker {

    /**Получение данных от пользователя.*/
    private Input input;

    /**Хранилище заявок*/
    private Tracker tracker;
    private static final int QTY = 15;

    private UserAction[] actions = new UserAction[QTY];

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public int[] range() {
        int[] temp = new int[QTY];
        int i;
        for (i = 0; actions[i] != null && i < actions.length; i++) {
            temp[i] = actions[i].key();
        }
        return Arrays.copyOf(temp, i + 1);
    }

    public void fillActions(StartUI ui) {
        actions[0] = new CreateItem();
        actions[1] = new MenuTracker.ShowAllItem();
        actions[2] = this.new EditItem();
        actions[3] = new DeleteItem();
        actions[4] = new FindByNameItem();
        actions[5] = new FindByIdItem();
        actions[6] = new Out(ui);
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
    public static class ShowAllItem extends BaseAction {
        public ShowAllItem() {
            super(1, "Показать все заявки");
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
            if (finded.length == 0) {
                System.out.println("------------ Заявок нет --------------");
            }
        }
    }

    /**
     * отредактировать заявку
     */
    public class EditItem  extends BaseAction {
        public EditItem() {
            super(2, "Редактировать заявку");
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Редактирование заявки--------------");
            String id = input.ask("Введите id заявки :");
            Item old = tracker.findById(id);
            if (old != null) {
                String newName = input.ask("Введите новое имя заявки :");
                String newDesc = input.ask("Введите новый текст заявки :");
                Item item = new Item(newName, newDesc);
                tracker.replace(id, item);
                System.out.println("------------ Заявка изменена --------------");
            } else {
                System.out.println("------------ Заявка не найдена --------------");
            }
        }
    }

    /**
     * удалить заявку
     */
    public class DeleteItem  extends BaseAction {
        public DeleteItem() {
            super(3, "Удалить заявку");
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Удаление заявки --------------");
            String id = input.ask("Введите ID заявки");
            boolean del = tracker.delete(id);
            if (del) {
                System.out.println("------------ Заявка удалена --------------");
            } else {
                System.out.println("------------ Заявка не найдена --------------");
            }
        }
    }

    /**
     * найти по имени
     */
    public class FindByNameItem  extends BaseAction {
        public FindByNameItem() {
            super(4, "Найти заявку по имени");
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
            if (finded.length == 0) {
                System.out.println("------------ Заявки не найдены --------------");
            }
        }
    }

    /**
     * найти по ID
     */
    public class FindByIdItem  extends BaseAction {
        public FindByIdItem() {
            super(5, "Найти заявку по ID");
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Найти заявку по ID --------------");
            String id = input.ask("Введите ID заявки");
            Item finded = tracker.findById(id);
            if (finded != null) {
                System.out.println("--------Заявка: " + id);
                System.out.println("Имя: " + finded.getName());
                System.out.println("Дата создания: " + finded.getCreated());
                System.out.println(finded.getDesc());
            } else {
                System.out.println("------------ Заявка не найдена --------------");
            }
        }
    }

    /**
     * Выйти
     */
    public class Out extends BaseAction {
        public Out(StartUI ui) {
            super(6, "Выйти");
            this.ui = ui;
        }

        private StartUI ui;

        @Override
        public void execute(Input input, Tracker tracker) {
            ui.stop();
        }
    }
}
