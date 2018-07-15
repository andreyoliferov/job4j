package list;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @autor Андрей
 * @since 15.07.2018
 */
public class MyLinkedListTest {

    private MyLinkedList<Integer> dal;

    @BeforeMethod
    public void testStart() {
        dal = new MyLinkedList<>();
        dal.add(1);
        dal.add(2);
    }

    @Test
    public void whenGetElements() {
        assertThat(dal.get(0), is(2));
        assertThat(dal.get(1), is(1));
    }

    @Test(expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void whenGetElementOutArrayThenException() {
        dal.get(2);
    }

    @Test
    public void whenIteration() {
        Iterator it = dal.iterator();
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void whenArrayModeIteratorException() {
        Iterator it = dal.iterator();
        it.next();
        it.next();
        it.next();
    }

    @Test(expectedExceptions = ConcurrentModificationException.class)
    public void whenArrayOutIteratorException() {
        Iterator it = dal.iterator();
        dal.add(3);
        it.next();
    }

    @Test
    public void whenDeleteLast() {
        assertThat(dal.deleteLast(), is(1));
        assertThat(dal.get(0), is(2));
    }

    @Test
    public void whenDeleteFirst() {
        assertThat(dal.deleteFirst(), is(2));
        assertThat(dal.get(0), is(1));
    }

    @Test
    public void whenDeleteOfIndex() {
        dal.add(3);
        assertThat(dal.delete(1), is(2));
        assertThat(dal.get(0), is(3));
        assertThat(dal.get(1), is(1));
    }
}
