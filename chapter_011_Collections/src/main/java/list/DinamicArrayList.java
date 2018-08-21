package list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

/**
 * @autor Андрей
 * @since 15.07.2018
 */
@ThreadSafe
public class DinamicArrayList<E> implements Iterable<E>, MySimpleList<E> {

    @GuardedBy("this")
    private Object[] container;

    //private int modCount = 0;
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

    public synchronized int getSizeArray() {
        return this.size;
    }

    @Override
    public synchronized int getSize() {
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
        //this.modCount++;
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
            private List copy = Arrays.asList(container);
            private int indexCopy = index;
            private int i = 0;
            //private int modeIt = modCount;

            @Override
            public boolean hasNext() {
                return i < indexCopy;
            }

            @Override
            public E next() {
//                if (modCount != modeIt) {
//                    throw new ConcurrentModificationException();
//                }
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) copy.get(i++);
            }
        };
    }
}