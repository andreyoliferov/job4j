package ru.job4j.array;

/**
 * Классический поиск перебором. [#33489]
 * @autor Андрей Олиферов
 * @since 09.05.2018
 */
public class FindLoop {

    /**
     * Метод ищет элемент в массиве и возвращает его индекс
     * @param data массив для поиска
     * @param elem искомый элемент
     * @return индекс найденного элемента
     */
    public int indexOf(int[] data, int elem) {
        int rst = -1;
        for (int i = 0; i < data.length; i++) {
            if (elem == data[i]) {
                rst = i;
                break;
            }
        }
        return rst;
    }
}
