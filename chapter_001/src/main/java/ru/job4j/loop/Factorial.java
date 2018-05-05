package ru.job4j.loop;

/**
 * 4.2. Создать программу вычисляющую факториал. [#193]
 * @autor Андрей
 * @since 05.05.2018
 */
public class Factorial {

    /**
     * метод вычисляет факториал числа n
     * @param n положительное целое число
     * @return факториал
     */
    public int calc(int n) {
        int factorial = 1;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
}
