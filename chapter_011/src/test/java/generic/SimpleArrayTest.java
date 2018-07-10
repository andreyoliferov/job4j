package generic;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertFalse;

/**
 * @autor Андрей
 * @since 10.07.2018
 */
public class SimpleArrayTest {

    private SimpleArray<String> sarray;

    @BeforeMethod
    public void start() {
        sarray = new SimpleArray<>(5);
        sarray.add("Andrey");
        sarray.add("Katya");
        sarray.add("Max");
    }

    @Test
    public void whenAddModel() {
        sarray.add("Sergey");
        assertThat(sarray.get(3), is("Sergey"));
    }

    @Test
    public void whenDeleteModel() {
        sarray.delete(0);
        assertFalse(sarray.get(0).equals("Andrey"));
    }

    @Test
    public void whenSetModel() {
        sarray.set(0, "Irina");
        assertThat(sarray.get(0), is("Irina"));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void whenUseSimpleArrayIterator() {
        Iterator<String> iterator = sarray.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("Andrey"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("Katya"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("Max"));
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }

    @Test(expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void whenOutArrayThenException() {
        SimpleArray<Integer> sint = new SimpleArray<>(1);
        sint.add(1);
        sint.add(2);
    }

    @Test
    public void whenAddModelInt() {
        SimpleArray<Integer> sint = new SimpleArray<>(1);
        sint.add(1);
        assertThat(sint.get(0), is(1));
    }
}
