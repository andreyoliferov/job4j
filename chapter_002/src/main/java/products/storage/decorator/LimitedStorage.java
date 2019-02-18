package products.storage.decorator;


import products.items.decorator.IFood;

/**
 * @autor aoliferov
 * @since 14.02.2019
 */
public class LimitedStorage<E extends IFood> extends DecoratorStorage<E> {

    private int size;

    public LimitedStorage(IStorage storage, int size) {
        super(storage);
        this.size = size;
    }

    @Override
    public boolean add(E item) {
        boolean result = false;
        if (super.engaged() < this.size) {
            result = super.add(item);
        }
        return result;
    }
}
