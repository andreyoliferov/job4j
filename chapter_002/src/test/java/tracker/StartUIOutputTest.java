package tracker;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @autor Андрей Олиферов
 * @since 26.05.2018
 */
public class StartUIOutputTest {

    /** стрекер */
    private Tracker tracker;
    /** переменная для хранения данных вывода на консоль */
    private ByteArrayOutputStream out =  new ByteArrayOutputStream();
    /** вывод на консоль */
    private final PrintStream stdout = new PrintStream(out);
    /** Функция вывода */
    private final Consumer<String> output = stdout::println;

    @BeforeMethod
    public void loadOutput() {
        tracker = new Tracker();
        System.setOut(stdout);
    }

    @AfterMethod
    public void backOutput() {
        System.setOut(System.out);
    }

    /**
     * Конструктор ожидаемой строки c данными заявки
     * @return String
     */
    private String dataBuild(String id, String name, String desc, String created) {
        return String.format("Заявка{id='%s', name='%s', desc='%s', created='%s'}", id, name, desc, created);
    }

    /**
     * проверка вывода
     * @param expect ожидаемый результат
     * @param data заявка toString
     */
    private void checkOutput(Boolean expect, String data) {
        assertThat(new String(out.toByteArray()).contains(data), is(expect));
    }

    @Test
    public void whenUserSelectShowAllItem() {
        tracker.add(new Item("заявка1", "Описание1"));
        tracker.add(new Item("заявка2", "Описание2"));
        Input stubInput = new StubInput(Arrays.asList("1", "6"));
        new StartUI(stubInput, tracker, output).init();
        checkOutput(true, dataBuild(tracker.findAll().get(0).getId(), "заявка1", "Описание1", tracker.findAll().get(0).getCreated()));
        checkOutput(true, dataBuild(tracker.findAll().get(1).getId(), "заявка2", "Описание2", tracker.findAll().get(1).getCreated()));
    }

    @Test
    public void whenUserFindItemForId() {
        tracker.add(new Item("заявка1", "Описание1"));
        tracker.add(new Item("заявка2", "Описание2"));
        tracker.add(new Item("заявка3", "Описание3"));
        String id = tracker.findAll().get(1).getId();
        Input stubInput = new StubInput(Arrays.asList("5", id, "6"));
        new StartUI(stubInput, tracker, output).init();
        checkOutput(true, dataBuild(id, "заявка2", "Описание2", tracker.findAll().get(1).getCreated()));
        checkOutput(false, dataBuild(tracker.findAll().get(0).getId(), "заявка1", "Описание1", tracker.findAll().get(0).getCreated()));
    }

    @Test
    public void whenUserFindItemForName() {
        String name = "заявка2";
        tracker.add(new Item("заявка1", "Описание1"));
        tracker.add(new Item(name, "Описание2"));
        tracker.add(new Item(name, "Описание3"));
        Input stubInput = new StubInput(Arrays.asList("4", name, "6"));
        new StartUI(stubInput, tracker, output).init();
        checkOutput(true, dataBuild(tracker.findAll().get(1).getId(), name, "Описание2", tracker.findAll().get(1).getCreated()));
        checkOutput(true, dataBuild(tracker.findAll().get(2).getId(), name, "Описание3", tracker.findAll().get(2).getCreated()));
        checkOutput(false, dataBuild(tracker.findAll().get(0).getId(), "заявка1", "Описание1", tracker.findAll().get(0).getCreated()));
    }

    @Test
    public void whenUserShowMenu() {
        Input stubInput = new StubInput(Arrays.asList("6"));
        new StartUI(stubInput, tracker, output).init();
        StringBuilder build = new StringBuilder();
        build.append("0. Добавить новую заявку").append(System.lineSeparator());
        build.append("1. Показать все заявки").append(System.lineSeparator());
        build.append("2. Редактировать заявку").append(System.lineSeparator());
        build.append("3. Удалить заявку").append(System.lineSeparator());
        build.append("4. Найти заявку по имени").append(System.lineSeparator());
        build.append("5. Найти заявку по ID").append(System.lineSeparator());
        build.append("6. Выйти").append(System.lineSeparator());
        checkOutput(true, build.toString());
    }

    @Test
    public void whenHaveNotOrdersShowAll() {
        Input stubInput = new StubInput(Arrays.asList("1", "1", "6"));
        new StartUI(stubInput, tracker, output).init();
        checkOutput(true, "------------ Заявок нет --------------");
    }

    @Test
    public void whenHaveNotOrdersEditItem() {
        Input stubInput = new StubInput(Arrays.asList("2", "1", "6"));
        new StartUI(stubInput, tracker, output).init();
        checkOutput(true, "------------ Заявка не найдена --------------");
    }

    @Test
    public void whenHaveNotOrdersDeleteItem() {
        Input stubInput = new StubInput(Arrays.asList("3", "1", "6"));
        new StartUI(stubInput, tracker, output).init();
        checkOutput(true, "------------ Заявка не найдена --------------");
    }

    @Test
    public void whenHaveNotOrdersFindByName() {
        Input stubInput = new StubInput(Arrays.asList("4", "1", "6"));
        new StartUI(stubInput, tracker, output).init();
        checkOutput(true, "------------ Заявки не найдены --------------");
    }

    @Test
    public void whenHaveNotOrdersFindById() {
        Input stubInput = new StubInput(Arrays.asList("5", "1", "6"));
        new StartUI(stubInput, tracker, output).init();
        checkOutput(true, "------------ Заявка не найдена --------------");
    }
}
