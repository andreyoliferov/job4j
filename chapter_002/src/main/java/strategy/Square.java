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
        pic.append("+++++++\n");
        pic.append("+++++++\n");
        pic.append("+++++++\n");
        pic.append("+++++++\n");
        return pic.toString();
    }
}
