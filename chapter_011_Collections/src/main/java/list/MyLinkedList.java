package list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 15.07.2018
 */
@ThreadSafe
public class MyLinkedList<E> implements Iterable<E>, MySimpleList<E> {

    @GuardedBy("this")
    private Node<E> first;

    //private int mode = 0;
    private int size = 0;

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
    @Override
    public synchronized void add(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.next = this.first;
        this.first = newNode;
        size++;
        //mode++;
    }

    /**
     * удалить первый элемент
     */
    public synchronized E deleteFirst() {
        return delete(0);
    }

    /**
     * удалить последний элемент
     */
    public synchronized E deleteLast() {
        return delete(size - 1);
    }

    /**
     * удалить последний элемент
     */
    public synchronized E delete(int position) {
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
        //mode++;
        return delete.data;
    }

    /**
     * вернуть элемент
     * @param position позиция элемента
     * @return элемент
     */
    @Override
    public synchronized E get(int position) {
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
    public synchronized int getSize() {
        return this.size;
    }


    @Override
    public Iterator<E> iterator() {
        MyLinkedList copy = new MyLinkedList();
        Node<E> temp = first;
        for (int i = 0; i < size; i++) {
            copy.add(temp.data);
            temp = temp.next;
        }
        return new Iterator<E>() {
            private int i = 0;
//            private int modeIt = mode;
            Node<E> temp = copy.first;

            @Override
            public boolean hasNext() {
                return i < copy.size;
            }

            @Override
            public E next() {
//                if (mode != modeIt) {
//                 throw new ConcurrentModificationException();
//                }
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                Node result = temp;
                temp = temp.next;
                i++;
                return (E) result.data;
            }
        };
    }
}

