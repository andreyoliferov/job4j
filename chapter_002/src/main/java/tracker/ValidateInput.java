package tracker;

/**
 * @autor Андрей
 * @since 27.05.2018
 */
public class ValidateInput extends ConsoleInput {

    public int ask(String question, int[] range) {
        Boolean invalid = true;
        int value = -1;
        do {
            try {
                value = super.ask(question, range);
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
