package products.storage;


import products.items.Food;

/**
 * @autor aoliferov
 * @since 14.02.2019
 */
public class LimitedStorage extends DecoratorStorage {

    private int size;

    public LimitedStorage(IStorage storage, int size) {
        super(storage);
        this.size = size;
    }

    @Override
    public boolean add(Food item) {
        boolean result = false;
        if (super.engaged() < this.size) {
            result = super.add(item);
        }
        return result;
    }
}
