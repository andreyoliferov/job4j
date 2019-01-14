package io;

import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @autor aoliferov
 * @since 09.01.2019
 */
public class ServiceIO {

    /**
     * true если в потоке есть четное число
     * @param in входящий поток
     * @return
     */
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

    /**
     * Удаление всех перечисленных в массиве слов из входящего потока, запись в исходящий
     * @param in вход
     * @param out выход
     * @param abuse исключения
     * @throws IOException
     */
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
