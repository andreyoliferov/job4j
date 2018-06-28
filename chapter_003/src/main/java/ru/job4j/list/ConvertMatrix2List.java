package ru.job4j.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor Андрей
 * @since 28.06.2018
 */
public class ConvertMatrix2List {

    public List<Integer> toList(int[][] array) {
        List<Integer> list = new ArrayList<>();
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                list.add(array[x][y]);
            }
        }
        return list;
    }

}
