package ru.job4j.user;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @autor Андрей
 * @since 30.06.2018
 */
public class UserConvertTest {

    @Test
    public void whenListUserConvertHashMap() {
        UserConvert convert = new UserConvert();
        User andrey = new User("Andrey", 28, "Omsk");
        User maksim = new User("Maksim", 19, "Nivosibirsk");
        User katya = new User("Katya", 26, "Moskva");
        List<User> list = List.of(andrey, maksim, katya);
        HashMap<UUID, User> result = convert.process(list);
        list.forEach(user -> assertThat(result.get(user.getID()), Matchers.is(user)));
    }

}
