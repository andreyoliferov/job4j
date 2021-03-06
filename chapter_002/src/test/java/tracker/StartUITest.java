package tracker;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertTrue;

/**
 * @autor Андрей Олиферов
 * @since 19.05.2018
 */
public class StartUITest {

    private Tracker tracker;
    private final Consumer<String> output = System.out::println;

    @BeforeMethod
    private void init() {
        tracker = new Tracker();
    }

    @Test
    public void whenUserAddItemThenTrackerHasItem() {
        Input stubInput = new StubInput(Arrays.asList("0", "имя1", "описание1", "6"));
        new StartUI(stubInput, tracker, output).init();
        assertThat(tracker.findAll().get(0).getName(), is("имя1"));
        assertThat(tracker.findAll().get(0).getDesc(), is("описание1"));
        assertTrue(tracker.findAll().get(0).getCreated()
                .contains(LocalDateTime.now().withNano(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @Test
    public void whenUserAddThreeItemThenTrackerHasThreeItem() {
        Input stubInput = new StubInput(Arrays.asList("0", "1", "1", "0", "2", "2", "0", "3", "3", "6"));
        new StartUI(stubInput, tracker, output).init();
        assertThat(tracker.findAll().size(), is(3));
    }

    @Test
    public void whenUserDeleteItemThenTrackerHasNotItem() {
        tracker.add(new Item("заявка1", "Описание1"));
        tracker.add(new Item("заявка2", "Описание2"));
        tracker.add(new Item("заявка3", "Описание3"));
        String id = tracker.findAll().get(1).getId();
        Input stubInput = new StubInput(Arrays.asList("3", id, "6"));
        new StartUI(stubInput, tracker, output).init();
        assertThat(tracker.findAll().size(), is(2));
        assertThat(tracker.findAll().get(0).getName(), is("заявка1"));
        assertThat(tracker.findAll().get(1).getName(), is("заявка3"));
    }

    @Test
    public void whenUserEditItemThenTrackerHasEditedItem() {
        tracker.add(new Item("заявка1", "Описание1"));
        String id = tracker.findAll().get(0).getId();
        Input stubInput = new StubInput(Arrays.asList("2", id, "новое имя", "новое описание", "6"));
        new StartUI(stubInput, tracker, output).init();
        assertThat(tracker.findAll().get(0).getName(), is("новое имя"));
        assertThat(tracker.findAll().get(0).getDesc(), is("новое описание"));
    }
}
