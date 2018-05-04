package ru.job4j.max;

/**
 * 3.2. Максимум из двух чисел [#189]
 * @autor Андрей Олиферов
 * @since 04.05.2018
 */
public class Max {

    /**
     * Метод возвращает максимальное значение из 2х.
     * @param first первое число
     * @param second второе число
     * @return максимальное значение из 2х чисел
     */
    public int maxOfTwoNunber(int first, int second) {
        return first > second ? first : second;
    }

    /**
     * Метод возвращает максимальное значение из 3х
     * @param first первое число
     * @param second второе число
     * @param third третье число
     * @return максимальное значение из 3х
     */
    public int maxOfThreeNumbers(int first, int second, int third) {
        int temp = this.maxOfTwoNunber(first, second);
        return this.maxOfTwoNunber(temp, third);
    }
}
