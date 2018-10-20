package tracker;

import java.sql.SQLException;
import java.util.List;

/**
 * @autor aoliferov
 * @since 17.10.2018
 */
public interface ITracker {

    Item add(Item item);
    boolean replace(String id, Item item);
    boolean delete(String id);
    List<Item> findAll();
    List<Item> findByName(String key);
    Item findById(String id);

}
