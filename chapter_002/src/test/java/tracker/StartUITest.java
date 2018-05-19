package tracker;

import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @autor Андрей Олиферов
 * @since 19.05.2018
 */
public class StartUITest {

    @Test
    public void whenUserAddItemThenTrackerHasItem() {
        Tracker tracker = new Tracker();
        Input stubInput = new StubInput(new String[] {"0", "имя1", "описание1", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("имя1"));
        assertThat(tracker.findAll()[0].getDesc(), is("описание1"));
        assertTrue(tracker.findAll()[0].getCreated().contains(DateFormat.getDateInstance().format(new Date())));
    }

    @Test
    public void whenUserAddThreeItemThenTrackerHasThreeItem() {
        Tracker tracker = new Tracker();
        Input stubInput = new StubInput(new String[] {"0", "1", "1", "0", "2", "2", "0", "3", "3", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll().length, is(3));
    }

    @Test
    public void whenUserDeleteItemThenTrackerHasNotItem() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("заявка1", "Описание1"));
        tracker.add(new Item("заявка2", "Описание2"));
        tracker.add(new Item("заявка3", "Описание3"));
        String id = tracker.findAll()[1].getId();
        Input stubInput = new StubInput(new String[] {"3", id, "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll().length, is(2));
        assertThat(tracker.findAll()[0].getName(), is("заявка1"));
        assertThat(tracker.findAll()[1].getName(), is("заявка3"));
    }

    @Test
    public void whenUserEditItemThenTrackerHasEditedItem() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("заявка1", "Описание1"));
        String id = tracker.findAll()[0].getId();
        Input stubInput = new StubInput(new String[] {"2", id, "новое имя", "новое описание", "6"});
        new StartUI(stubInput, tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("новое имя"));
        assertThat(tracker.findAll()[0].getDesc(), is("новое описание"));
    }
}
