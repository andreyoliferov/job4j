package todo.store;

import todo.Item;

import java.util.List;

/**
 * @autor aoliferov
 * @since 26.02.2019
 */
public interface Store {

    void addTask(Item item) throws Exception;
    void deleteTask(Item item) throws Exception;
    void updateTask(Item item) throws Exception;
    List<Item> getAll();
    List getUnfulfilled();

}
