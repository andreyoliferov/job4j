package trackersql;

import tracker.ITracker;
import tracker.Item;

import java.util.List;

/**
 * @autor aoliferov
 * @since 17.10.2018
 */
public class TrackerSQL implements ITracker {


    @Override
    public Item add(Item item) {
        return null;
    }

    @Override
    public boolean replace(String id, Item item) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public List<Item> findByName(String key) {
        return null;
    }

    @Override
    public Item findById(String id) {
        return null;
    }
}
