package ru.job4j.calculator;

/**
 * @author Андрей Олиферов
 * @since  02.05.2018
 */
public class Calculator {
    /**
     * Поле результат.
     */
    private double result;

    /**
     * Сложение занчений переменных.
     * @param first 1-е знаечние
     * @param second 2-е значение
     */
    public void add(double first, double second) {
        this.result = first + second;
    }

    /**
     * Вычитание занчений переменных.
     * @param first 1-е знаечние
     * @param second 2-е значение
     */
    public void subtract(double first, double second) {
        this.result = first - second;
    }

    /**
     * Делениие занчений переменных.
     * @param first 1-е знаечние
     * @param second 2-е значение
     */
    public void div(double first, double second) {
        this.result = first / second;
    }

    /**
     * Умножение занчений переменных.
     * @param first 1-е знаечние
     * @param second 2-е значение
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }

    /**
     * Метод возвращиет значение result
     * @return result
     */
    public double getResult() {
        return this.result;
    }
}
