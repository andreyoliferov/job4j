package trackerDB;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс обертка для массива заявок
 * @autor Андрей Олиферов
 * @since 18.05.2018
 */
public class Tracker implements AutoCloseable {
    private List<Item> items = new ArrayList<>();
    private Connection connection;

    @Override
    public void close() throws Exception {

    }

    /**
     * добавить элемент
     * @param item
     * @return
     */
    public Item add(Item item) {
        this.items.add(item);
        return item;
    }

    /**
     * заменить элемент
     * @param id
     * @param newItem
     */
    public boolean replace(String id, Item newItem) {
        boolean repl = false;
        for (Item item : items) {
            if (item.getId().equals(id)) {
                newItem.setId(item.getId());
                this.items.set(items.indexOf(item), newItem);
                repl = true;
                break;
            }
        }
        return repl;
    }

    /**
     * удалить элемент
     * @param id
     */
    public boolean delete(String id) {
        boolean del = false;
        for (Item item : items) {
            if (item.getId().equals(id)) {
                items.remove(item);
                del = true;
                break;
            }
        }
        return del;
    }

    /**
     * найти и вернуть все элементы
     * @return
     */
    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    /**
     * найти элемент по имени
     * @param key
     * @return
     */
    public List<Item> findByName(String key) {
        List<Item> temp = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                temp.add(item);
            }
        }
        return temp;
    }

    /**
     * найти элемент по id
     * @param id
     * @return
     */
    public Item findById(String id) {
        Item finded = null;
        for (Item item : items) {
            if (item.getId().equals(id)) {
                finded = item;
                break;
            }
        }
        return finded;
    }
}
