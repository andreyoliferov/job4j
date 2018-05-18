package tracker;

import java.util.Random;

/**
 * Класс обертка для массива заявок
 * @autor Андрей Олиферов
 * @since 18.05.2018
 */
public class Tracker {
    Item[] items = new Item[100];
    private int position = 0;
    private static final Random RN = new Random();

    /**
     * добавить элемент
     * @param item
     * @return
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items[this.position++] = item;
        return item;
    }

    /**
     * генерация уникального id
     * @return
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextLong());
    }

    /**
     * заменить элемент
     * @param id
     * @param newItem
     */
    public void replace(String id, Item newItem) {
        for (int i = 0; i < position; i++) {
            if (this.items[i].getId().equals(id)) {
                this.items[i] = newItem;
                break;
            }
        }
    }

    /**
     * удалить элемент
     * @param id
     */
    public void delete(String id) {
        for (int i = 0; i < position; i++) {
            if (items[i].getId().equals(id)) {
                Item[] temp = items;
                System.arraycopy(temp, i + 1, items, i,  position - (i + 1));
                if (position == items.length) {
                    items[items.length] = null;
                }
                position--;
                break;
            }
        }
    }

    /**
     * найти и вернуть все элементы
     * @return
     */
    public Item[] findAll() {
        Item[] all = new Item[position];
        System.arraycopy(items, 0, all, 0, position);
        return all;
    }

    /**
     * найти элемент по имени
     * @param key
     * @return
     */
    public Item findByName(String key) {
        Item finded = null;
        for (Item item : items) {
            if (item.getName().equals(key)) {
                finded = item;
                break;
            }
        }
        return finded;
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
