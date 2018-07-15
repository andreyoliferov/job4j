package list;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @autor Андрей
 * @since 15.07.2018
 */
public class SimpleStackTest {

    @Test
    public void whenPull() {
        SimpleStack<Integer> ss = new SimpleStack<>();
        ss.push(1);
        ss.push(2);
        ss.push(3);
        assertThat(ss.poll(), is(3));
        assertThat(ss.poll(), is(2));
        assertThat(ss.poll(), is(1));
    }
}
