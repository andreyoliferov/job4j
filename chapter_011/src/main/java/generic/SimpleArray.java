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

    private void checkIndexOut(int position) {
        if (position >= this.index) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void add(T model) {
        this.objects[this.index++] = model;
    }

    public void set(int position, T model) {
        this.checkIndexOut(position);
        this.objects[position] = model;
    }

    public boolean set(Object obj, T model) {
        boolean done = false;
        for (int i = 0; i < index; i++) {
            if (this.objects[i].equals(obj)) {
                this.set(i, model);
                done = true;
                break;
            }
        }
        return done;
    }

    public void delete(int position) {
        this.checkIndexOut(position);
        Object[] temp = objects;
        System.arraycopy(temp, position + 1, objects, position,  index - (position + 1));
        index--;
        objects[index] = null;
    }

    public boolean delete(Object obj) {
        boolean done = false;
        for (int i = 0; i < index; i++) {
            if (objects[i].equals(obj)) {
                Object[] temp = objects;
                System.arraycopy(temp, i + 1, objects, i,  index - (i + 1));
                index--;
                objects[index] = null;
                done = true;
                break;
            }
        }
        return done;
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
