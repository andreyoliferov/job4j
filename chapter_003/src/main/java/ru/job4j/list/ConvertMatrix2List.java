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
        for (int[] out : array) {
            for (int in : out) {
                list.add(in);
            }
        }
        return list;
    }

}
