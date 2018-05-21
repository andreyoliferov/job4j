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
        pic.append("   +   \n");
        pic.append("  +++  \n");
        pic.append(" +++++ \n");
        pic.append("+++++++\n");
        return pic.toString();
    }
}
