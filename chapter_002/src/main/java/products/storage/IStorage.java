package products.storage;

import products.items.Food;

/**
 * @autor aoliferov
 * @since 14.02.2019
 */
public interface IStorage {

    boolean add(Food item);
    int engaged();

}
