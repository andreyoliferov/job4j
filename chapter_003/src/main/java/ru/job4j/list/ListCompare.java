package ru.job4j.list;

import java.util.Comparator;

/**
 * @autor Андрей
 * @since 01.07.2018
 */
public class ListCompare implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        int n = Math.min(o1.length(), o2.length());
        int result = 0;
        for (int i = 0; i < n; i++) {
            result = Character.compare(o1.toCharArray()[i], o2.toCharArray()[i]);
            if (result != 0) {
                break;
            }
        }
        result =  result == 0 ? o1.length() - o2.length() : result;
        return result;
    }
}
