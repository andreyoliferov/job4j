package set;

import list.DinamicArrayList;


/**
 * @autor Андрей
 * @since 16.07.2018
 */
public class SimpleSet<E> extends AbstractSet<E> {

    public SimpleSet() {
        super(new DinamicArrayList<>());
    }
}
