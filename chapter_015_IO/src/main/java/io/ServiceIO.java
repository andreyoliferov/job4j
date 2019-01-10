package io;

import java.io.*;
import java.util.Arrays;
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

    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) throws IOException {
        try (Scanner sc = new Scanner(in);
            OutputStreamWriter ow = new OutputStreamWriter(out)) {
            while (sc.hasNext()) {
                String s = sc.next();
                if (!Arrays.asList(abuse).contains(s)) {
                    ow.append(s).append(" ");
                }
            }
            ow.flush();
        }
    }
}
