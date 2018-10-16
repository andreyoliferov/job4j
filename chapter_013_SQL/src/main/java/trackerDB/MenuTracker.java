package trackerDB;

import java.util.ArrayList;
import java.util.List;

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

    private List<UserAction> actions = new ArrayList<>();

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public List<Integer> range() {
        List<Integer> temp = new ArrayList<>();
        for (UserAction action : actions) {
            temp.add(action.key());
        }
        return temp;
    }

    public void fillActions(StartUI ui) {
        actions.add(new CreateItem());
        actions.add(new MenuTracker.ShowAllItem());
        actions.add(this.new EditItem());
        actions.add(new DeleteItem());
        actions.add(new FindByNameItem());
        actions.add(new FindByIdItem());
        actions.add(new Out(ui));
    }

    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
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
            List<Item> finded = tracker.findAll();
            int i = 1;
            for (Item item : finded) {
                System.out.println(i++ + ") " + item);
            }
            if (finded.size() == 0) {
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
            List<Item> finded = tracker.findByName(name);
            for (Item item : finded) {
                System.out.println(item);
            }
            if (finded.size() == 0) {
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
                System.out.println(finded);
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
