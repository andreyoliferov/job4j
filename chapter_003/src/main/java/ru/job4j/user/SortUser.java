package ru.job4j.user;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @autor Андрей
 * @since 01.07.2018
 */
public class SortUser {

    public Set<User> base(List<User> list) {
        Set<User> result = new TreeSet<>();
        result.addAll(list);
        return result;
    }
}
