package ru.job4j.user;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @autor Андрей
 * @since 01.07.2018
 */
public class SortUserTest {

    @Test
    public void whenUserBaseSortThenSorted() {
        SortUser sort = new SortUser();
        User andrey = new User("Andrey", 28, "Omsk");
        User andrey2 = new User("Andrey", 16, "Kirov");
        User olya = new User("Olya", 16, "Saint-Pitersburd");
        User maksim = new User("Maksim", 19, "Nivosibirsk");
        User katya = new User("Katya", 26, "Moskva");
        List<User> users = List.of(andrey, maksim, andrey2, olya, katya);
        List<User> sorted = new ArrayList<>(sort.base(users));
        List<User> except = List.of(andrey2, olya, maksim, katya, andrey);
        assertThat(sorted, Matchers.is(except));
    }

    @Test
    public void whenUserSortByLengthNameThenSorted() {
        SortUser sort = new SortUser();
        User andrey = new User("Andreyy", 28, "Omsk");
        User olya = new User("Olya", 16, "Saint-Pitersburd");
        User maksim = new User("Maksim", 19, "Nivosibirsk");
        User katya = new User("Katya", 26, "Moskva");
        List<User> users = new ArrayList<>(List.of(andrey, maksim, olya, katya));
        List<User> sorted = sort.sortNameLength(users);
        List<User> except = List.of(olya, katya, maksim, andrey);
        assertThat(sorted, Matchers.is(except));
    }

    @Test
    public void whenUserSortByAllFieldThenSorted() {
        SortUser sort = new SortUser();
        User andrey = new User("Andrey", 28, "Omsk");
        User andrey2 = new User("Andrey", 16, "Kirov");
        User andrey3 = new User("Andrey", 16, "Abakan");
        User andrey4 = new User("Andrey", 17, "Omsk");
        User olya = new User("Olya", 28, "Omsk");
        User katya = new User("Katya", 19, "Nivosibirsk");
        User katya2 = new User("Katya", 26, "Moskva");
        List<User> users = new ArrayList<>(List.of(katya, katya2, olya, andrey4, andrey3, andrey2, andrey));
        List<User> except = List.of(andrey3, andrey2, andrey4, andrey, katya, katya2, olya);
        List<User> sorted = sort.sortByAllFields(users);
        assertThat(sorted, Matchers.is(except));
    }


}
