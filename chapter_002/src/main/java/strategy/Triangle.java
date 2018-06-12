package strategy;

/**
 * Форма Треугольник
 * @autor Андрей Олиферов
 * @since 21.05.2018
 */
public class Triangle implements Shape {

    /**
     * Метод рисует треугольник
     * @return String
     */
    @Override
    public String pic() {
        StringBuilder pic = new StringBuilder();
        pic.append("   +   ").append(System.lineSeparator())
                .append("  +++  ").append(System.lineSeparator())
                .append(" +++++ ").append(System.lineSeparator())
                .append("+++++++").append(System.lineSeparator());
        return pic.toString();
    }
}
