package tracker;

import java.util.Scanner;

/**
 * Консольный ввод данных
 * @autor Андрей
 * @since 19.05.2018
 */
public class ConsoleInput implements Input{

    private Scanner scanner = new Scanner(System.in);

    /**
     * Метод возвразает ответ на вопрос
     * @param question вопрос
     * @return ответ
     */
    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

}
