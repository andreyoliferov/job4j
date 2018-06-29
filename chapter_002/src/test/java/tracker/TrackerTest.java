package tracker;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @autor Андрей Олиферов
 * @since 19.05.2018
 */
public class TrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription");
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription");
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2");
        next.setId(previous.getId());
        tracker.replace(previous.getId(), next);
        System.out.println(previous.getCreated());
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    @Test
    public void whenDeleteItemThenReturn() {
        Tracker tracker = new Tracker();
        Item one = new Item("test1", "testDescription");
        Item two = new Item("test2", "testDescription2");
        Item three = new Item("test3", "testDescription3");
        tracker.add(one);
        tracker.add(two);
        tracker.add(three);
        tracker.delete(two.getId());
        assertThat(tracker.findAll(), is(Arrays.asList(one, three)));
    }

    @Test
    public void whenFindByNameThenReturnItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        Item itemTwo = new Item("test2", "testDescription1");
        Item itemTree = new Item("test1", "testDescription1");
        tracker.add(item);
        tracker.add(itemTwo);
        tracker.add(itemTree);
        assertThat(tracker.findByName("test1"), is(Arrays.asList(item, itemTree)));
    }
}
