package io;

import java.io.*;
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
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
            String temp = sb.toString();
            for (String s1 : abuse) {
                temp = temp.replaceAll(s1, "");
            }
            temp = temp.replaceAll(" {2}", " ");
            ow.append(temp);
            ow.flush();
        }
    }
}
