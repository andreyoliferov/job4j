package ru.job4j.list;

import java.util.Comparator;

/**
 * @autor Андрей
 * @since 01.07.2018
 */
public class ListCompare implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        boolean revers = false;
        if (o1.length() > o2.length()) {
            String buff = o1;
            o1 = o2;
            o2 = buff;
            revers = true;
        }
        int result = 0;
        int i = 0;
        for (char ch1 : o1.toCharArray()) {
            result = Character.compare(ch1, o2.toCharArray()[i++]);
            if (result != 0) {
                break;
            }
        }
        result = i == o1.length() && result == 0 && i < o2.length() ? -1 : result;
        result = revers ? result * -1 : result;
        return result;
    }
}
