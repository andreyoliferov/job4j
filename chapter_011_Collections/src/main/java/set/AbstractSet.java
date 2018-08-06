package set;

import list.MySimpleList;

import java.util.Iterator;

/**
 * @autor Андрей
 * @since 16.07.2018
 */
public abstract class AbstractSet<E> {

    public AbstractSet(MySimpleList<E> data) {
        this.data = data;
    }

    private MySimpleList<E> data;

    /**
     * добавить уникальное значение
     * @param value значение
     * @return булево - успех
     */
    public boolean add(E value) {
        boolean done = false;
        if (!this.contains(value)) {
            data.add(value);
            done = true;
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

    public Iterator<E> iterator() {
        return data.iterator();
    }
}
