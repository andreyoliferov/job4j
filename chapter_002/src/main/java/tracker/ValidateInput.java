package tracker;

/**
 * @autor Андрей
 * @since 27.05.2018
 */
public class ValidateInput implements Input {

    private final Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    public int ask(String question, int[] range) {
        Boolean invalid = true;
        int value = -1;
        do {
            try {
                value = input.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Выбите значение из диапазона!");
            } catch (NumberFormatException nfe) {
                System.out.println("Введите числовое значение!");
            }
        } while (invalid);
        return value;
    }

}
