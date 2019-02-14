package products;

import products.items.Food;
import products.storage.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor aoliferov
 * @since 13.02.2019
 */
public class ControllQuality {

    /**
     * Перераспределить товары на складах.
     * @param storages
     */
    public void perform(Storage ... storages) {
        List<Food> unsuitable = new ArrayList<>();
        for (Storage store : storages) {
            unsuitable.addAll(store.clear());
        }
        distribute(unsuitable, storages);
    }

    /**
     * Распределить поставку.
     * @param delivery
     * @param storages
     */
    public void distribute(List<Food> delivery, Storage ... storages) {
        List<Food> unsuitable = delivery;
        for (Storage store : storages) {
            unsuitable = store.addAll(unsuitable);
        }
    }
}
