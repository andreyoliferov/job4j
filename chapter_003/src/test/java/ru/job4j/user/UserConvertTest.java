package ru.job4j.user;

import org.hamcrest.Matchers;
import org.junit.Test;

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
        List<User> list = new ArrayList<>();
        User andrey = new User("Andrey", "Omsk");
        User maksim = new User("Maksim", "Nivosibirsk");
        User katya = new User("Katya", "Moskva");
        list.addAll(Arrays.asList(andrey, maksim, katya));
        HashMap<UUID, User> result = convert.process(list);
        list.forEach(user -> assertThat(result.get(user.getID()), Matchers.is(user)));
    }

}
