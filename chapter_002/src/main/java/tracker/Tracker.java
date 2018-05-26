package tracker;

import java.util.UUID;

/**
 * Класс обертка для массива заявок
 * @autor Андрей Олиферов
 * @since 18.05.2018
 */
public class Tracker {
    private Item[] items = new Item[100];
    private int position = 0;

    /**
     * добавить элемент
     * @param item
     * @return
     */
    public Item add(Item item) {
        this.items[this.position++] = item;
        return item;
    }

    /**
     * генерация уникального id
     * @return
     */
    private String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * заменить элемент
     * @param id
     * @param newItem
     */
    public void replace(String id, Item newItem) {
        for (int i = 0; i < position; i++) {
            if (this.items[i].getId().equals(id)) {
                newItem.setId(this.items[i].getId());
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
                position--;
                items[position] = null;
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
    public Item[] findByName(String key) {
        Item[] temp = new Item[position];
        int count = 0;
        for (int i = 0; i < position; i++) {
            if (items[i].getName().equals(key)) {
                temp[count] = items[i];
                count++;
            }
        }
        Item[] finded = new Item[count];
        System.arraycopy(temp, 0, finded, 0, count);
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
