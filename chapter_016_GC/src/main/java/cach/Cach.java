package cach;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor aoliferov
 * @since 23.02.2019
 */
public abstract class Cach<K, V> {

    /** Данные кэша. */
    private Map<K, SoftReference<V>> data = new HashMap<>();

    /**
     * Получение данных из кеша.
     * Если данные были уничтожены GC, загрузка новых данных.
     * @param key
     * @return
     */
    public V get(K key) {
        V result = data.get(key).get();
        if (result == null) {
            result = add(key);
        }
        return result;
    }

    /**
     * Добавление данных в кеш.
     */
    public V add(K key) {
        V result = load(key);
        data.put(key, new SoftReference<>(result));
        return result;
    }

    /**
     * Метод загрузки данных. Реализация зависит от расположения данных (db, файлб сеть)
     */
    protected abstract V load(K key);
}
