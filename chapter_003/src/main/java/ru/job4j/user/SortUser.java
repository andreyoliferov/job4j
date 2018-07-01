package ru.job4j.user;

import java.util.*;

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

    public List<User> sortNameLength(List<User> list) {
        Comparator<User> nameLength = (o1, o2) -> {
            Integer lengthName1 = o1.getName().length();
            Integer lengthName2 = o2.getName().length();
            return lengthName1.compareTo(lengthName2);
        };
        list.sort(nameLength);
        return list;
    }

    public List<User> sortByAllFields(List<User> list) {
        Comparator<User> name = Comparator.comparing(User::getName);
        Comparator<User> age = Comparator.comparing(User::getAge);
        Comparator<User> city = Comparator.comparing(User::getCity);
        list.sort(name.thenComparing(age.thenComparing(city)));
        return list;
    }
}
