package ru.job4j.condition;

/**
 * Класс расчитывает расстояние между двумя точками
 * @autor Andrey Oliferov
 * @since  02.05.2018.
 */
public class Point {

    /**
     * значение по оси х.
     */
    private int x;

    /**
     * значение по оси y.
     */
    private int y;

    /**
     * Точка с координатами х/у
     * @param x значение по оси х.
     * @param y значение по оси y.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод расчитывает расстояние между 2-мя точками
     * @param that экземпляр точки
     * @return расстояниедо точки
     */
    public double distanceTo(Point that) {
        return Math.sqrt(Math.pow(that.x - this.x, 2) + Math.pow(that.y - this.y, 2));
    }
}
