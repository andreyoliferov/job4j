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
public class DinamicArrayList<E> implements Iterable<E>, MySimpleList<E> {

    @GuardedBy("this")
    private Object[] container;

    @GuardedBy("this")
    private int modCount = 0;

    private int index = 0;
    private int size = 10;
    private static final double MULTIPLIER = 0.75;

    /**
     * конструктор для массива произвольного размера
     * @param size размер массива
     */
    public DinamicArrayList(int size) {
        this.container = new Object[size];
        this.size = size;
    }

    /**
     * конструктор для масива размера по умолчанию
     */
    public DinamicArrayList() {
        this.container = new Object[this.size];
    }

    public int getSizeArray() {
        return this.size;
    }

    @Override
    public int getSize() {
        return this.index;
    }

    /**
     * увеличить размер массива
     */
    private synchronized void enlargeSize() {
        if (index > MULTIPLIER * size) {
            Object[] temp = container;
            size *= 2;
            container = new Object[size];
            System.arraycopy(container, 0, temp, 0, index - 1);
        }
    }

    /**
     * добавить элемент
     * @param value элемент
     */
    @Override
    public synchronized void add(E value) {
        this.container[index++] = value;
        this.enlargeSize();
        this.modCount++;
    }

    /**
     * получить элемент
     * @param position позиция в массиве
     * @return элемент
     */
    @Override
    public synchronized E get(int position) {
        if (position >= this.index) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return (E) container[position];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int mode = modCount;

            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < index;
            }

            @Override
            public E next() {
                if (this.mode != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) container[i++];
            }
        };
    }
}