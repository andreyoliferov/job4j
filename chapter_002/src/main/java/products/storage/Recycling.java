package products.storage;

import products.items.decorator.ReproductedFood;
import java.util.function.Predicate;

/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public class Recycling extends Storage<ReproductedFood> {

    protected Recycling(Predicate<ReproductedFood> condition) {
        super(condition);
    }

    @Override
    public boolean add(ReproductedFood item) {
        boolean result = false;
        if (item.isCanReproduct()) {
            result = super.add(item);
        }
        return result;
    }
}
