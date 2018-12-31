package tracker.singleton;

import org.testng.annotations.Test;
import tracker.Tracker;

/**
 * @autor aoliferov
 * @since 31.12.2018
 */
public class SingleLazyInnerClassTrackerTest {

    @Test
    public void whenGetTwoInstanceThenReturnOneObject() {
        Tracker trackOne = SingleLazyInnerClassTracker.getInstance();
        Tracker trackTwo = SingleLazyInnerClassTracker.getInstance();
        assert trackTwo == trackOne;
    }
}
