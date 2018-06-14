package tracker;

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
     * заменить элемент
     * @param id
     * @param newItem
     */
    public boolean replace(String id, Item newItem) {
        boolean repl = false;
        for (int i = 0; i < position; i++) {
            if (this.items[i].getId().equals(id)) {
                newItem.setId(this.items[i].getId());
                this.items[i] = newItem;
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
        for (int i = 0; i < position; i++) {
            if (items[i].getId().equals(id)) {
                Item[] temp = items;
                System.arraycopy(temp, i + 1, items, i,  position - (i + 1));
                position--;
                items[position] = null;
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
        for (int i = 0; i < position; i++) {
            if (items[i].getId().equals(id)) {
                finded = items[i];
                break;
            }
        }
        return finded;
    }
}
