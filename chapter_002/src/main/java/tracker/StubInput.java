package tracker;

/**
 * @autor Андрей Олиферов
 * @since 19.05.2018
 */
public class StubInput implements Input {

    public StubInput(final String[] value) {
        this.value = value;
    }

    /** Значения ответов */
    private final String[] value;

    /** Счетчик */
    private int position = 0;

    /**
     * Метод автоматически взвращает ответы из поля value
     */
    @Override
    public String ask(String question) {
        return this.value[this.position++];
    }
}
