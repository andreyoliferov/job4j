package products.storage;

import products.items.Food;

/**
 * @autor aoliferov
 * @since 14.02.2019
 */
public abstract class DecoratorStorage implements IStorage {

    private IStorage storage;

    protected DecoratorStorage(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean add(Food item) {
        return storage.add(item);
    }

    @Override
    public int engaged() {
        return storage.engaged();
    }
}
