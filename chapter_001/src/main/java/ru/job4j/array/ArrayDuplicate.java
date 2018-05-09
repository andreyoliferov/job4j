package ru.job4j.array;

import java.util.Arrays;

/**
 * 5.6. Удаление дубликатов в массиве. [#225]
 * @autor Андрей Олиферов
 * @since 09.05.2018
 */
public class ArrayDuplicate {

    /**
     * Метод удаляет дубликаты в массиве строк
     * @param array исходный массив
     * @return массив уникальных значений
     */
    public String[] remove(String[] array) {
        int uniq = array.length;
        for (int out = 0; out < uniq; out++) {
            for (int in = out + 1; in < uniq; in++) {
                if (array[out].equals(array[in])) {
                    array[in] = array[uniq - 1];
                    uniq--;
                    in--;
                }
            }
        }
        return Arrays.copyOf(array, uniq);
    }
}
