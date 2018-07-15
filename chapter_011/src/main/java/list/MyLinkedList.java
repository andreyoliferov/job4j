package list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 15.07.2018
 */
public class MyLinkedList<E> implements Iterable<E> {

    public Node<E> first;
    public int size = 0;
    private int mode = 0;

    private static class Node<E> {

        E data;
        Node<E> next;

        private Node(E data) {
            this.data = data;
        }
    }

    /**
     * добавить элемент
     * @param value элемент
     */
    public void add(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.next = this.first;
        this.first = newNode;
        size++;
        mode++;
    }

    /**
     * вернуть элемент
     * @param position позиция элемента
     * @return элемент
     */
    public E get(int position) {
        Node<E> temp = this.first;
        for (int i = 0; i < position; i++) {
            temp = temp.next;
        }
        return temp.data;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int i = 0;
            private int modeIt = mode;
            Node<E> temp = first;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public E next() {
                if (mode != modeIt) {
                 throw new ConcurrentModificationException();
                }
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                Node result = temp;
                temp = temp.next;
                return (E) result.data;
            }
        };
    }
}

