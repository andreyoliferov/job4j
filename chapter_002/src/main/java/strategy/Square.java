package strategy;

/**
 * Форма Квадрат
 * @autor Андрей Олиферов
 * @since 21.05.2018
 */
public class Square implements Shape {

    /**
     * Метод рисует квадрат
     * @return String
     */
    @Override
    public String pic() {
        StringBuilder pic = new StringBuilder();
        pic.append("+++++++").append(System.lineSeparator())
                .append("+++++++").append(System.lineSeparator())
                .append("+++++++").append(System.lineSeparator())
                .append("+++++++").append(System.lineSeparator());
        return pic.toString();
    }
}
