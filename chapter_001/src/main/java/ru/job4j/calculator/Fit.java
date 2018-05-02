package ru.job4j.calculator;

/**
 * Программа расчета идеального веса
 * @author Andrey Oliferov
 * @since  02.05.2018.
 */
public class Fit {

    /**
     * Идеальный вес для мужщины.
     * @param height Рост.
     * @return идеальный вес.
     */
    public double manWeight(double height) {
        return (height - 100D) * 1.15D;
    }

    /**
     * Идеальный вес для женщины.
     * @param height Рост.
     * @return идеальный вес.
     */
    public double womanWeight(double height) {
        return (height - 110D) * 1.15D;
    }
}
