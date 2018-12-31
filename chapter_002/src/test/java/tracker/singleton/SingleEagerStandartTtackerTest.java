package tracker.singleton;

import org.testng.annotations.Test;
import tracker.Tracker;

/**
 * @autor aoliferov
 * @since 31.12.2018
 */
public class SingleEagerStandartTtackerTest {

    @Test
    public void whenGetTwoInstanceThenReturnOneObject() {
        Tracker trackOne = SingleEagerStandartTtacker.getInstance();
        Tracker trackTwo = SingleEagerStandartTtacker.getInstance();
        assert trackTwo == trackOne;
    }
}
