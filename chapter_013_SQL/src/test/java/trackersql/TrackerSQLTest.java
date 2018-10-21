package trackersql;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import tracker.Item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @autor Андрей Олиферов
 * @since 19.05.2018
 */
public class TrackerSQLTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL()) {
            Item item = new Item("test1", "testDescription");
            tracker.add(item);
            assertThat(tracker.findById(item.getId()), is(item));
            tracker.delete(item.getId());
            assertThat(tracker.findById(item.getId()), Matchers.nullValue());
        }
    }
}
