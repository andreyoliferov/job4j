package tracker;

import java.util.Scanner;

/**
 * Консольный ввод данных
 * @autor Андрей
 * @since 19.05.2018
 */
public class ConsoleInput implements Input {

    private Scanner scanner = new Scanner(System.in);

    /**
     * Метод возвразает ответ на вопрос
     * @param question вопрос
     * @return ответ
     */
    @Override
    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public int ask(String question, int[] range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int n : range) {
            if (n == key) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        } else {
            throw new MenuOutException("Выход из диапазона!");
        }
    }
}
