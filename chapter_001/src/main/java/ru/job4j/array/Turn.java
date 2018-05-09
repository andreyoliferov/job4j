package ru.job4j.array;

/**
 * 5.2. Перевернуть массив. [#4441]
 * @autor Андрей
 * @since 09.05.2018
 */
public class Turn {

    /**
     * метод переворачивает массив
     * @param array исходный массив
     * @return перевернутый массив
     */
    public int[] turn(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int lastIndex = array.length - 1;
            int temp = array[i];
            array[i] = array[lastIndex - i];
            array[lastIndex - i] = temp;
        }
        return array;
    }
}
