package list;

import java.util.Iterator;

/**
 * @autor Андрей
 * @since 16.07.2018
 */
public interface MySimpleList<E> extends Iterable<E> {

    void add(E value);

    E get(int position);

    int getSize();

    @Override
    Iterator<E> iterator();
}
