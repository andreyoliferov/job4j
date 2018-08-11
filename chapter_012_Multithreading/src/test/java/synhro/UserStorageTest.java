package synhro;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import synchro.User;
import synchro.UserStorage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @autor Андрей
 * @since 11.08.2018
 */
public class UserStorageTest {

    private UserStorage store;
    private User one;
    private User two;

    @BeforeMethod
    public void init() {
        store = new UserStorage();
        one = new User(1000);
        two = new User(500);
        store.add(one);
        store.add(two);
    }


    @Test
    public void whenTransfer() {
        boolean bool = store.transfer(one.getId(), two.getId(), 1000);
        assertThat(bool, is(true));
    }

    @Test
    public void whenDeleteThenNotTransfer() {
        store.delete(two);
        boolean bool = store.transfer(one.getId(), two.getId(), 1000);
        assertThat(bool, is(false));
    }

    @Test
    public void when10ThreadThenTransfer() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int k = 0; k < 10; k++) {
                    store.transfer(one.getId(), two.getId(), 1);
                }
            }).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int k = 0; k < 10; k++) {
                    store.transfer(two.getId(), one.getId(), 2);
                }
            }).start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(one.getAmount(), is(1100));
        assertThat(two.getAmount(), is(400));
    }

}
