package tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @autor Андрей Олиферов
 * @since 26.05.2018
 */
public class StartUIOutputTest {

    /** стрекер */
    private Tracker tracker = new Tracker();
    /** переменная для хранения данных вывода на консоль */
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    /** стандартный вывод на консоль */
    private PrintStream stdout = System.out;

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
    }

    /**
     * Конструктор ожидаемой строки
     * @param lines ожидаемые строки в консоли в массиве
     * @return String
     */
    private String strBuild(String[] lines) {
        StringBuilder build = new StringBuilder();
        for (String line : lines) {
            build.append(line).append(System.lineSeparator());
        }
        return build.toString();
    }

    /**
     * проверка вывода
     * @param expect ожидаемый результат
     * @param lines массив строк консоли
     */
    private void checkOutput(Boolean expect, String[] lines) {
        assertThat(new String(out.toByteArray())
                .contains(strBuild(lines)), is(expect));
    }

    @Test
    public void whenUserSelectShowAllItem() {
        tracker.add(new Item("заявка1", "Описание1"));
        tracker.add(new Item("заявка2", "Описание2"));
        Input stubInput = new StubInput(new String[] {"1", "6"});
        new StartUI(stubInput, tracker).init();
        checkOutput(true, new String[]
                {"заявка1", "Описание1", tracker.findAll()[0].getId(), tracker.findAll()[0].getCreated()});
        checkOutput(true, new String[]
                {"заявка2", "Описание2", tracker.findAll()[1].getId(), tracker.findAll()[1].getCreated()});
    }

    @Test
    public void whenUserFindItemForId() {
        tracker.add(new Item("заявка1", "Описание1"));
        tracker.add(new Item("заявка2", "Описание2"));
        tracker.add(new Item("заявка3", "Описание3"));
        String id = tracker.findAll()[1].getId();
        Input stubInput = new StubInput(new String[] {"5", id, "6"});
        new StartUI(stubInput, tracker).init();
        checkOutput(true,
                new String[] {"Заявка: " + id, "Имя: заявка2", "Дата создания: " + tracker.findAll()[1].getCreated(),
                        tracker.findAll()[1].getDesc()});
        checkOutput(false, new String[] {
                "Заявка: " + tracker.findAll()[0].getId(),
                "Имя: заявка1",
                "Дата создания: " + tracker.findAll()[0].getCreated(),
                tracker.findAll()[0].getDesc()});
    }

    @Test
    public void whenUserFindItemForName() {
        String name = "заявка2";
        tracker.add(new Item("заявка1", "Описание1"));
        tracker.add(new Item(name, "Описание2"));
        tracker.add(new Item(name, "Описание3"));
        Input stubInput = new StubInput(new String[] {"4", name, "6"});
        new StartUI(stubInput, tracker).init();
        checkOutput(true, new String[] {
                "Заявка: " + name,
                "ID: " + tracker.findAll()[1].getId(),
                "Дата создания: " + tracker.findAll()[1].getCreated(),
                tracker.findAll()[1].getDesc()});
        checkOutput(true, new String[] {
                "Заявка: " + name,
                "ID: " + tracker.findAll()[2].getId(),
                "Дата создания: " + tracker.findAll()[2].getCreated(),
                tracker.findAll()[2].getDesc()});
        checkOutput(false, new String[] {
                "Заявка: " + name,
                "ID: " + tracker.findAll()[0].getId(),
                "Дата создания: " + tracker.findAll()[0].getCreated(),
                tracker.findAll()[0].getDesc()});
    }

    @Test
    public void whenUserShowMenu() {
        Input stubInput = new StubInput(new String[] {"6"});
        new StartUI(stubInput, tracker).init();
        checkOutput(true, new String[] {
                "0. Добавить новую заявку",
                "1. Показать все заявки",
                "2. Редактировать заявку",
                "3. Удалить заявку",
                "4. Найти заявку по имени",
                "5. Найти заявку по ID",
                "6. Выйти"});
    }
}
