package iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 08.07.2018
 */
public class EvenNumbersIterator implements Iterator {

    public EvenNumbersIterator(int[] array) {
        this.even = returnEven(array);
    }

    private List<Integer> even;
    private int index = 0;

    @Override
    public boolean hasNext() {
        return even.size() > index;
    }

    @Override
    public Object next() {
        Object o;
        if (this.hasNext()) {
            o = even.get(index++);
        } else {
            throw new NoSuchElementException();
        }
        return o;
    }

    private List<Integer> returnEven(int[] array) {
        List<Integer> even = new ArrayList<>();
        for (int value : array) {
            if (value % 2 == 0) {
                even.add(value);
            }
        }
        return even;
    }
}
