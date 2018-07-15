package list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 15.07.2018
 */
public class DinamicArrayList<E> implements Iterable<E> {

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

    private Object[] container;
    private int index = 0;
    private int size = 10;
    private double multiplier = 0.75;
    private int modCount = 0;

    public int getSizeArray() {
        return this.size;
    }

    public int getSize() {
        return this.index;
    }

    /**
     * увеличить размер массива
     */
    private void enlargeSize() {
        if (index > multiplier * size) {
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
    public void add(E value) {
        this.container[index++] = value;
        this.enlargeSize();
        this.modCount++;
    }

    /**
     * получить элемент
     * @param position позиция в массиве
     * @return элемент
     */
    public E get(int position) {
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