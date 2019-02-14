package products.storage;

import products.items.Food;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @autor aoliferov
 * @since 13.02.2019
 */
public abstract class Storage implements IStorage {

    /**
     * Список продуктов.
     */
    protected List<Food> storage = new LinkedList<>();

    /**
     * Условия хранения.
     */
    protected Predicate<Food> condition;

    protected Storage(Predicate<Food> condition) {
        this.condition = condition;
    }

    /**
     * @return список всех продуктов.
     */
    public List<Food> getAll() {
        return storage;
    }

    @Override
    public int engaged() {
        return storage.size();
    }

    /**
     * Добавить список продуктов.
     * @param items список продуктов.
     * @return список продуктов, не подходящих под условия хранения.
     */
    public List<Food> addAll(List<Food> items) {
        return items.stream().filter((food) -> !this.add(food)).collect(Collectors.toList());
    }

    /**
     * Добавить 1 элемент.
     * @param item продукт.
     * @return false если продукт не подошел под условия хранения.
     */
    @Override
    public boolean add(Food item) {
        boolean result = condition.test(item);
        if (result) {
            storage.add(item);
        }
        return result;
    }

    /**
     * Очистить хранилище от неподходящих продуктов.
     * @return список неподходящих продуктов.
     */
    public List<Food> clear() {
        Iterator<Food> iterator = storage.iterator();
        List<Food> removed = new ArrayList<>();
        while (iterator.hasNext()) {
            Food f = iterator.next();
            if (!condition.test(f)) {
                removed.add(f);
                iterator.remove();
            }
        }
        return removed;
    }

}
