package map;

import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertTrue;

/**
 * @autor Андрей
 * @since 22.07.2018
 */
public class MapTests {

    @Test
    public void whenPutInMap() {

        Calendar calendar1 = new GregorianCalendar();
        calendar1.set(1989, 7, 19);

        Calendar calendar2 = new GregorianCalendar();
        calendar2.set(1994, 9, 3);

        Calendar calendar3 = new GregorianCalendar();
        calendar3.set(1985, 6, 12);

        Map<User, Object> map = new HashMap<>();
        map.put(new User("Andrey", 0, calendar1), new Object());
        map.put(new User("Katya", 1, calendar2), new Object());
        map.put(new User("Boris", 3, calendar3), new Object());

        System.out.println(map);
    }
}
