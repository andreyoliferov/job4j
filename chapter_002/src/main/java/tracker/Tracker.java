package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс обертка для массива заявок
 * @autor Андрей Олиферов
 * @since 18.05.2018
 */
public class Tracker implements ITracker {
    private List<Item> items = new ArrayList<>();

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
        return items.stream().filter(item -> item.getId().equals(id))
                .peek(item -> {
                    newItem.setId(item.getId());
                    this.items.set(items.indexOf(item), newItem);
                })
                .findFirst()
                .isPresent();
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
        return items.stream()
                .filter(item -> item.getName().equals(key))
                .collect(Collectors.toList());
    }

    /**
     * найти элемент по id
     * @param id
     * @return
     */
    public Item findById(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst().orElse(null);
    }
}
