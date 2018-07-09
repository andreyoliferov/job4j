package iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 08.07.2018
 */
public class MatrixIterator implements Iterator {

    public MatrixIterator(int[][] matrix) {
        this.matrix = matrix;
    }

    private int[][] matrix;
    private int indexIn = 0;
    private int indexOut = 0;

    @Override
    public boolean hasNext() {
        return matrix.length > indexOut;
    }

    @Override
    public Object next() {
        Object o;
        if (this.hasNext()) {
            o = matrix[indexOut][indexIn++];
            if (!(matrix[indexOut].length > indexIn)) {
                indexOut++;
                indexIn = 0;
            }
        } else {
            throw new NoSuchElementException();
        }
        return o;
    }
}
