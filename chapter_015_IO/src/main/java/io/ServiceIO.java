package io;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @autor aoliferov
 * @since 09.01.2019
 */
public class ServiceIO {

    public boolean isEvenNumber(InputStream in) {
        boolean result = false;
        try (Scanner sc = new Scanner(in)) {
            while (sc.hasNext() && !result) {
                if (sc.hasNextInt()) {
                    result =  sc.nextInt() % 2 == 0;
                } else {
                    sc.next();
                }
            }
        }
        return result;
    }
}
