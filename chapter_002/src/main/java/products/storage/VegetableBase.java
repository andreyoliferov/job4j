package products.storage;

import products.items.Vegetable;
import java.util.function.Predicate;

/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public class VegetableBase extends Storage<Vegetable> {

    private int temperature;

    protected VegetableBase(Predicate<Vegetable> condition) {
        super(condition);
    }

    @Override
    public boolean add(Vegetable item) {
        boolean result = false;
        if (item.getStorageTemperature().getFrom() < this.temperature
                && item.getStorageTemperature().getTo() > this.temperature) {
            result = super.add(item);
        }
        return result;
    }
}
