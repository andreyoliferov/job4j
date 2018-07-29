package map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * @autor Андрей
 * @since 29.07.2018
 */
public class MyHashMapTest {

    private MyHashMap<Integer, String> myMap;

    @BeforeMethod
    public void start() {
        myMap = new MyHashMap<>(4);
        myMap.insert(1, "один");
        myMap.insert(2, "два");
    }

    @Test
    public void whenAddThen() {
        boolean result = myMap.insert(3, "три");
        String value = myMap.get(3);
        assertThat(result, is(true));
        assertThat(value, is("три"));
    }

    @Test
    public void whenAddExistThenReturnFalse() {
        boolean result = myMap.insert(2, "двадва");
        String value = myMap.get(2);
        assertThat(result, is(false));
        assertThat(value, is("два"));
    }

    @Test
    public void wheDelete() {
        boolean result = myMap.delete(1);
        String value = myMap.get(1);
        assertThat(result, is(true));
        assertThat(value, nullValue());
    }

    @Test
    public void wheDeleteNoExist() {
        boolean result = myMap.delete(3);
        assertThat(result, is(false));
    }

    @Test
    public void whenResize() {
        myMap.insert(3, "один");
        myMap.insert(4, "один");
        assertThat(myMap.insert(5, "один"), is(true));
        assertThat(myMap.get(5), is("один"));
    }
}
