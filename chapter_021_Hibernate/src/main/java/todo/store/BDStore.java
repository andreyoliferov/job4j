package todo.store;

import todo.Item;

import java.util.List;

/**
 * @autor aoliferov
 * @since 26.02.2019
 */
public class BDStore implements Store {

    @Override
    public void addTask(Item item) {
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public List<Item> getUnfulfilled() {
        return null;
    }
}
