package ru.job4j.user;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @autor Андрей
 * @since 01.07.2018
 */
public class UserTest {


    @DataProvider
    private Object[][] data() {
        return new Object[][]{
                {new User("Andrey", 28, "Omsk"),  new User("Andrey", 28, "Omsk"), 0},
                {new User("Andrey", 16, "Omsk"),  new User("Olya", 16, "Omsk"), -1},
                {new User("Andrey", 17, "Omsk"),  new User("Olya", 16, "Omsk"), 1},
                {new User("Olya", 18, "Omsk"),  new User("Olya", 19, "Omsk"), -1}
        };
    }

    @Test(dataProvider = "data")
    public void whenCompareUsersThenReturnExpect(User one, User two, int expect) {
        Integer base = one.compareTo(two);
        assertThat(base.compareTo(0), is(expect));
    }


}
