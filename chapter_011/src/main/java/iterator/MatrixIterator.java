package iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 08.07.2018
 */
public class MatrixIterator implements Iterator {

    public MatrixIterator(int[][] matrix) {
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
        try {
            return matrixList.get(index++);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
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
