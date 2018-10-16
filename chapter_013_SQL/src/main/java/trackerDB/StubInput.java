package trackerDB;

import java.util.List;

/**
 * @autor Андрей Олиферов
 * @since 19.05.2018
 */
public class StubInput implements Input {

    public StubInput(final List<String> value) {
        this.value = value;
    }

    /** Значения ответов */
    private final List<String> value;

    /** Счетчик */
    private int position = 0;

    /**
     * Метод автоматически взвращает ответы из поля value
     */
    @Override
    public String ask(String question) {
        return this.value.get(this.position++);
    }

    @Override
    public int ask(String question, List<Integer> range) {
        return  Integer.parseInt(this.value.get(this.position++));
    }
}
