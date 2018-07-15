package list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 15.07.2018
 */
public class MyLinkedList<E> implements Iterable<E> {

    private Node<E> first;
    private int size = 0;
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
     * удалить первый элемент
     */
    public E deleteFirst() {
        return delete(0);
    }

    /**
     * удалить последний элемент
     */
    public E deleteLast() {
        return delete(size - 1);
    }

    /**
     * удалить последний элемент
     */
    public E delete(int position) {
        if (position >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<E> temp = this.first;
        Node<E> delete;
        if (position == 0) {
            delete = this.first;
            this.first = first.next;
        } else {
            for (int i = 0; i < position - 1; i++) {
                temp = temp.next;
            }
            delete = temp.next;
            temp.next = temp.next.next;
        }
        size--;
        mode++;
        return delete.data;
    }

    /**
     * вернуть элемент
     * @param position позиция элемента
     * @return элемент
     */
    public E get(int position) {
        if (position >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
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
                return i <= size;
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

