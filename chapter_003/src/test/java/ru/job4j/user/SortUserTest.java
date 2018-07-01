package ru.job4j.user;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @autor Андрей
 * @since 01.07.2018
 */
public class SortUserTest {

    @Test
    public void whenUserSortThenSorted() {
        SortUser sort = new SortUser();
        List<User> users = new ArrayList<>();
        Set<User> sorted;
        User andrey = new User("Andrey", 28, "Omsk");
        User andrey2 = new User("Andrey", 16, "Kirov");
        User olya = new User("Olya", 16, "Saint-Pitersburd");
        User maksim = new User("Maksim", 19, "Nivosibirsk");
        User katya = new User("Katya", 26, "Moskva");
        users.addAll(Arrays.asList(andrey, maksim, andrey2, olya, katya));
        sorted = sort.base(users);
        List<User> except = new ArrayList<>();
        except.addAll(Arrays.asList(andrey2, olya, maksim, katya, andrey));
        int i = 0;
        for (User user : sorted) {
            assertThat(user, Matchers.is(except.get(i++)));
        }
    }

}
