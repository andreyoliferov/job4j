package tracker;

import org.junit.Test;

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
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll()[0], is(item));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2", 1234L);
        next.setId(previous.getId());
        tracker.replace(previous.getId(), next);
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    @Test
    public void whenDeleteItemThenReturn() {
        Tracker tracker = new Tracker();
        Item one = new Item("test1", "testDescription", 123L);
        Item two = new Item("test2", "testDescription2", 124L);
        Item three = new Item("test3", "testDescription3", 125L);
        tracker.add(one);
        tracker.add(two);
        tracker.add(three);
        tracker.delete(two.getId());
        assertThat(tracker.findAll(), is(new Item[] {one, three}));
    }

    @Test
    public void whenFindByNameThenReturnItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1", 123L);
        tracker.add(item);
        assertThat(tracker.findByName("test1"), is(item));
    }
}
