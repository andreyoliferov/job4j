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
        try (final PrintStream writer = new PrintStream(out);
             final BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            reader.lines()
                    .map(s -> Arrays.stream(abuse)
                            .reduce(s, (s1, s2) -> s1.replaceAll(s2, "")
                                    .replaceAll(" {2}", " ")
                            )
                    )
                    .forEach(writer::print);
        }
    }
}
