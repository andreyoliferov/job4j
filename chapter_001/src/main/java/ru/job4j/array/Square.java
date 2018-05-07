package ru.job4j.array;

/**
 * 5.0. Заполнить массив степенями чисел. [#33488]
 * @autor Андрей Олиферов
 * @since 07.05.2018
 */
public class Square {

    /**
     * @param bound число До
     * @return массив квадратов чисед дапазона от 1 до bound
     */
    public int[] calculate(int bound) {
        int[] rst = new int[bound];
        for (int i = 1; i <= bound; i++) {
            rst[i - 1] = i * i;
        }
        return rst;
    }
}
