package strategy;

/**
 * @autor Андрей
 * @since 21.05.2018
 */
public class Paint {

    /**
     * метод рисует форму
     * @param shape вариант реализации формы
     */
    public void draw(Shape shape){
        System.out.println(shape.pic());
    }

    public static void main(String[] args) {
        new Paint().draw(new Triangle());
    }
}
