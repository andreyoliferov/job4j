package set;

import list.MyLinkedList;

/**
 * @autor Андрей
 * @since 16.07.2018
 */
public class SimpleLinkedSet<E> extends AbstractSet<E> {

    public SimpleLinkedSet() {
        super(new MyLinkedList<>());
    }
}
