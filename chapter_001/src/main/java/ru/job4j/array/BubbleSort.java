package ru.job4j.array;

/**
 * 5.3. Создать программу для сортировки массива методом перестановки. [#195]
 * @autor Андрей
 * @since 09.05.2018
 */
public class BubbleSort {

    public int[] bubbleSort(int[] array) {
        int n = array.length - 1;
        for (int a = 1; a < array.length; a++) {
            for (int i = 0; i < n; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                }
            }
            n -= 1;
        }
        return array;
    }
}
