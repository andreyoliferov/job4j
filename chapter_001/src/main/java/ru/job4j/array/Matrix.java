package ru.job4j.array;

/**
 * 5.4. Двухмерный массив. Таблица умножения. [#33491]
 * @autor Андрей Олиферов
 * @since 09.05.2018
 */
public class Matrix {

    /**
     * Таблица умножения
     * @param size размер
     * @return двумерный массив таблицы умножения
     */
    public static int[][] table(int size) {
        int[][] array = new int[size][size];
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                array[j][i] = (j + 1) * (i + 1);
            }
        }
        return array;
    }
}
