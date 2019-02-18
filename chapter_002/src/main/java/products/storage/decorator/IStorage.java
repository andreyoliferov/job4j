package products.storage.decorator;

import products.items.decorator.IFood;

/**
 * @autor aoliferov
 * @since 14.02.2019
 */
public interface IStorage<E extends IFood> {

    boolean add(E item);
    int engaged();

}
