package iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 08.07.2018
 */
public class Converter {

    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {

            private Iterator<Integer> in = it.next();

            @Override
            public boolean hasNext() {
                return in.hasNext();
            }

            @Override
            public Integer next() {
                Integer n;
                if (this.hasNext()) {
                    n = in.next();
                    if (!in.hasNext() && it.hasNext()) {
                        in = it.next();
                    }
                } else {
                    throw new NoSuchElementException();
                }
                return n;
            }
        };
    }
}
