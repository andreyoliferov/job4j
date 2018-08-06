package store;

import generic.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @autor Андрей
 * @since 02.08.2018
 */
public class StoreTest {

    private Store store;
    private User one;
    private User two;
    private User three;
    private User threeCh;
    private User four;
    private User five;
    private User six;
    private User fourCh;

    {
        one = new User("1", "Andrey");
        two = new User("2", "Katya");
        three = new User("3", "Sergey");
        threeCh = new User("3", "Max");
        four = new User("4", "Maksim");
        fourCh = new User("4", "Elena");
        five = new User("5", "Katya");
        six = new User("6", "Katya");
    }

    @BeforeMethod
    public void testStart() {
        store = new Store();
    }


    @Test
    public void whenOther() {
        Info info = store.diff(Arrays.asList(four, six, three, two),
                Arrays.asList(two, one, threeCh, five, four));
        assertThat(info, is(new Info(2, 1, 1)));
    }

    @Test
    public void whenAllDeleted() {
        Info info = store.diff(Arrays.asList(four, six, three, two), Arrays.asList());
        assertThat(info, is(new Info(0, 0, 4)));
    }

    @Test
    public void whenAllChanged() {
        Info info = store.diff(Arrays.asList(three, four), Arrays.asList(fourCh, threeCh));
        assertThat(info, is(new Info(0, 2, 0)));
    }

    @Test
    public void whenAllAdd() {
        Info info = store.diff(new ArrayList<>(), Arrays.asList(four, five, six));
        assertThat(info, is(new Info(3, 0, 0)));
    }

    @Test
    public void whenEmptyArrays() {
        Info info = store.diff(new ArrayList<>(), new ArrayList<>());
        assertThat(info, is(new Info(0, 0, 0)));
    }

    @Test
    public void whenNoChange() {
        Info info = store.diff(Arrays.asList(two, one), Arrays.asList(one, two));
        assertThat(info, is(new Info(0, 0, 0)));
    }
}
