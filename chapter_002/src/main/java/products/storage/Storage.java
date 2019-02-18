package products.storage;

import products.items.decorator.IFood;
import products.storage.decorator.IStorage;

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
public abstract class Storage<E extends IFood> implements IStorage<E> {

    /**
     * Список продуктов.
     */
    protected List<E> storage = new LinkedList<>();

    /**
     * Условия хранения.
     */
    protected Predicate<E> condition;

    protected Storage(Predicate<E> condition) {
        this.condition = condition;
    }

    /**
     * @return список всех продуктов.
     */
    public List<E> getAll() {
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
    public List<E> addAll(List<E> items) {
        return items.stream().filter((food) -> !this.add(food)).collect(Collectors.toList());
    }

    /**
     * Добавить 1 элемент.
     * @param item продукт.
     * @return false если продукт не подошел под условия хранения.
     */
    @Override
    public boolean add(E item) {
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
    public List<E> clear() {
        Iterator<E> iterator = storage.iterator();
        List<E> removed = new ArrayList<>();
        while (iterator.hasNext()) {
            E f = iterator.next();
            if (!condition.test(f)) {
                removed.add(f);
                iterator.remove();
            }
        }
        return removed;
    }

}
