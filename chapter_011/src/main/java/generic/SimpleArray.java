package generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 10.07.2018
 */
public class SimpleArray<T> implements Iterable<T> {

    public SimpleArray(int size) {
        this.objects = new Object[size];
    }

    private Object[] objects;
    private int index = 0;

    public void add(T model) {
        this.objects[this.index++] = model;
    }

    public void set(int position, T model) {
        this.objects[position] = model;
    }

    public void delete(int position) {
        this.objects[position] = this.objects[this.index-- - 1];
    }

    public T get(int position) {
        return (T) this.objects[position];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < index;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) objects[i++];
            }
        };
    }
}
