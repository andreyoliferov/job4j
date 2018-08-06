package set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertTrue;

/**
 * @autor Андрей
 * @since 16.07.2018
 */
public class SimpleSetTest {

    private SimpleSet<Integer> simpleSet;

    @BeforeMethod
    public void startTest() {
        simpleSet = new SimpleSet<>();
        simpleSet.add(1);
        simpleSet.add(2);
    }

    @Test
    public void whenAddUniq() {
        assertThat(simpleSet.add(3), is(true));
        assertTrue(simpleSet.contains(3));
        assertThat(simpleSet.getSize(), is(3));
    }

    @Test
    public void whenAddUnuniq() {
        assertThat(simpleSet.add(2), is(false));
        assertTrue(simpleSet.contains(2));
        assertThat(simpleSet.getSize(), is(2));
    }
}
