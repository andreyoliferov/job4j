package generic;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @autor Андрей
 * @since 11.07.2018
 */
public class RoleStoreTest {

    RoleStore store;

    @BeforeMethod
    public void startTest() {
        store = new RoleStore();
        store.add(new Role("1"));
        store.add(new Role("2"));
        store.add(new Role("3"));
        store.add(new Role("4"));
    }

    @Test
    public void whenReplaseRole() {
        assertFalse(store.replace("7", new Role("5")));
        assertTrue(store.replace("1", new Role("5")));
    }

    @Test
    public void whenDeleteRole() {
        assertFalse(store.delete("7"));
        assertTrue(store.delete("3"));
    }

    @Test
    public void whenFindRole() {
        Role finded = store.findById("4");
        assertThat(finded, is(new Role("4")));
    }
}
