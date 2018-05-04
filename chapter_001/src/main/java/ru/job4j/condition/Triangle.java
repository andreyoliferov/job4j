package ru.job4j.condition;

/**
 * 3.3. Вычисление площади треугольника [#9461]
 * @autor Андрей Олиферов
 * @since 04.05.2018
 */
public class Triangle {

    /**
     * Точки
     */
    private Point a, b, c;

    /**
     * треугольник
     * @param a точка
     * @param b точка 2
     * @param c точка 3
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Метод возвращает булево = можно ли построить треугольник с такими длинами сторон.
     * @param ab Длина от точки a до b.
     * @param ac Длина от точки a до c.
     * @param bc Длина от точки b до c.
     * @return можно ли построить треугольник с такими длинами сторон.
     */
    private boolean exist(double ab, double ac, double bc) {
        return ab < ac + bc && ac < ab + bc && bc < ab + ac;
    }

    /**
     * Метод вычисления полупериметра по длинам сторон.
     * Формула:(ab + ac + bc) / 2
     * @param ab расстояние между точками a b
     * @param ac расстояние между точками a c
     * @param bc расстояние между точками b c
     * @return Перимент.
     */
    public double period(double ab, double ac, double bc) {
        return (ab + ac + bc) / 2;
    }

    /**
     * Метод вычисляет площадь треугольника.
     * @return прощадь, если треугольник существует или -1, если треугольника нет.
     */
    public double area() {
        double rsl = -1;
        double ab = this.a.distanceTo(this.b);
        double ac = this.a.distanceTo(this.c);
        double bc = this.b.distanceTo(this.c);
        double p = this.period(ab, ac, bc);
        if (this.exist(ab, ac, bc)) {
            rsl = Math.sqrt(p * (p - ab) * (p - ac) * (p - bc));
        }
        return rsl;
    }
}
