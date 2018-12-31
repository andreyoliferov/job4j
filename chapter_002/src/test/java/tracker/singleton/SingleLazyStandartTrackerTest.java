package tracker.singleton;

import org.testng.annotations.Test;
import tracker.Tracker;


/**
 * @autor aoliferov
 * @since 31.12.2018
 */
public class SingleLazyStandartTrackerTest {

    @Test
    public void whenGetTwoInstanceThenReturnOneObject() {
        Tracker trackOne = SingleLazyStandartTracker.getInstance();
        Tracker trackTwo = SingleLazyStandartTracker.getInstance();
        assert trackTwo == trackOne;
    }
}
