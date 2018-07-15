package list;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @autor Андрей
 * @since 15.07.2018
 */
public class SimpleArrayListTest {

    private SimpleArrayList<Integer> list;

    @BeforeMethod
    public void beforeTest() {
        list = new SimpleArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(1), is(2));
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(list.getSize(), is(3));
    }

    @Test
    public void whenDeleteElementsThenUseGetSizeResultTwo() {
        list.delete();
        assertThat(list.getSize(), is(2));
    }

    @Test
    public void whenDeleteElementsThenUseGetGetNollResultTwo() {
        list.delete();
        assertThat(list.get(0), is(2));
    }
}
