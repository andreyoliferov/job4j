package tracker.singleton;


import org.testng.annotations.Test;
import tracker.Tracker;

/**
 * @autor aoliferov
 * @since 31.12.2018
 */
public class SingleEagerEnumTrackerTest {

    @Test
    public void whenGetTwoInstanceThenReturnOneObject() {
        Tracker trackOne = SingleEagerEnumTracker.getInstanse();
        Tracker trackTwo = SingleEagerEnumTracker.getInstanse();
        assert trackTwo == trackOne;
    }
}
