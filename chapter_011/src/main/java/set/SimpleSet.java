package set;

import list.DinamicArrayList;

import java.util.Iterator;

/**
 * @autor Андрей
 * @since 16.07.2018
 */
public class SimpleSet<E> implements Iterable<E> {

    private DinamicArrayList<E> data = new DinamicArrayList<>();

    /**
     * добавить уникальное значение
     * @param value значение
     * @return булево - успех
     */
    public boolean add(E value) {
        boolean done = true;
        Iterator it = data.iterator();
        while (done && it.hasNext()) {
            if (value.equals(it.next())) {
                done = false;
            }
        }
        if (done) {
            data.add(value);
        }
        return done;
    }

    public int getSize() {
        return data.getSize();
    }

    public boolean contains(Object o) {
        boolean result = false;
        for (E e : data) {
            if (e.equals(o)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }
}
