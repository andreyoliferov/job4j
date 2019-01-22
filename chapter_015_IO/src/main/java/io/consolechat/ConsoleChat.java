package io.consolechat;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @autor aoliferov
 * @since 22.01.2019
 */
public class ConsoleChat {

    private boolean work = true;
    private boolean pause = false;

    private void app(InputStream in, OutputStream out) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintStream ps = new PrintStream(out);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("chapter_015_IO/src/main/java/io/consolechat/answers.txt"))
        );
        ps.println("//«стоп» для приостановки приложения");
        ps.println("//«продолжить» для возобновления работы");
        ps.println("//«закончить» для завершения работы приложения");
        ps.println("Введите вопрос:");
        List<String> answers = reader.lines().collect(Collectors.toList());
        int size = answers.size();
        while (work) {
            String question = br.readLine();
            func(question);
            if (!pause && work) {
                ps.println(answers.get(new Random().nextInt(size)));
            }
        }
    }

    private void func(String question) {
        if (question.equals("стоп")) {
            pause = true;
        }
        if (question.equals("продолжить")) {
            pause = false;
        }
        if (question.equals("закончить")) {
            work = false;
        }
    }

    public static void main(String[] args) throws IOException {
        ConsoleChat cc = new ConsoleChat();
        cc.app(System.in, System.out);
    }
}
