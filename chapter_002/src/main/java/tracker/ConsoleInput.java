package tracker;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

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

    /**
     * эксперимент с лямбдой
     */
    private final List<Consumer<Boolean>> valid = Arrays.asList(
            exist -> {
                if (!exist) {
                    throw new MenuOutException("Выход из диапазона!");
                }
            }
    );

    @Override
    public int ask(String question, List<Integer> range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = range.stream().anyMatch(n -> n == key);
        this.valid.forEach(action -> action.accept(exist));
        return key;
    }
}
