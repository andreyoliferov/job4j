package iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 08.07.2018
 */
public class MatrixIteratorList implements Iterator {

    public MatrixIteratorList(int[][] matrix) {
        this.matrixList = this.toList(matrix);
    }

    private List<Integer> matrixList;
    private int index = 0;

    @Override
    public boolean hasNext() {
        return matrixList.size() > index;
    }

    @Override
    public Object next() {
        Object o;
        if (this.hasNext()) {
            o = matrixList.get(index++);
        } else {
            throw new NoSuchElementException();
        }
        return o;
    }

    private List<Integer> toList(int[][] array) {
        List<Integer> list = new ArrayList<>();
        for (int[] out : array) {
            for (int in : out) {
                list.add(in);
            }
        }
        return list;
    }
}
