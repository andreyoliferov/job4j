package ru.job4j.list;

import java.util.Iterator;
import java.util.List;

/**
 * @autor Андрей
 * @since 28.06.2018
 */
public class ConvertList2Array {

    public int[][] toArray(List<Integer> list, int rows) {
        int cells = (int) Math.ceil ( (double) list.size() / rows);
        int[][] array = new int[rows][cells];
        Iterator<Integer> iterator = list.iterator();
        for (int x = 0; x < rows; x++) {
            for(int y = 0; y < cells; y++) {
                if (iterator.hasNext()) {
                    array[x][y] = iterator.next();
                } else {
                    array[x][y] = 0;
                }
            }
        }
        return array;
    }
}
