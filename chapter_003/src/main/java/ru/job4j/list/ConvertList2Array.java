package ru.job4j.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @autor Андрей
 * @since 28.06.2018
 */
public class ConvertList2Array {

    public int[][] toArray(List<Integer> list, int rows) {
        int cells = (int) Math.ceil((double) list.size() / rows);
        int[][] array = new int[rows][cells];
        int x = 0;
        int y = 0;
        for (Integer elem : list) {
            array[x][y] = elem;
            y++;
            if (y >= cells) {
                y = 0;
                x++;
            }
        }
        return array;
    }

    public List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for (int[] array : list) {
            for (int in : array) {
                result.add(in);
            }
        }
        return result;
    }
}
