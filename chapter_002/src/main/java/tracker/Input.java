package tracker;

/**
 * @autor Андрей
 * @since 19.05.2018
 */
public interface Input {

    String ask(String question);
    int ask(String question, int[] range);
}
