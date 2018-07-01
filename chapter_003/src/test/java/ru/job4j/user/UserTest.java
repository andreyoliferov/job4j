package ru.job4j.user;


import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @autor Андрей
 * @since 01.07.2018
 */
public class UserTest {


    @DataProvider
    private Object[][] data() {
        return new Object[][]{
                {new User("Andrey", 28, "Omsk"),  new User("Andrey", 28, "Omsk"), Matchers.is(0)},
                {new User("Andrey", 16, "Omsk"),  new User("Olya", 16, "Omsk"), Matchers.lessThan(0)},
                {new User("Andrey", 17, "Omsk"),  new User("Olya", 16, "Omsk"), Matchers.greaterThan(0)},
                {new User("Olya", 18, "Omsk"),  new User("Olya", 19, "Omsk"), Matchers.lessThan(0)}
        };
    }

    @Test(dataProvider = "data")
    public void whenCompareUsersThenReturnExpect(User one, User two, Matcher expect) {
        assertThat(one.compareTo(two), expect);
    }
}
