package products.storage.decorator;


import products.items.decorator.IFood;

/**
 * @autor aoliferov
 * @since 14.02.2019
 */
public abstract class DecoratorStorage<E extends IFood> implements IStorage<E> {

    private IStorage storage;

    protected DecoratorStorage(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean add(E item) {
        return storage.add(item);
    }

    @Override
    public int engaged() {
        return storage.engaged();
    }
}
