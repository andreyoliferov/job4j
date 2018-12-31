package tracker.singleton;

import tracker.Tracker;


/**
 * @autor aoliferov
 * @since 31.12.2018
 */
public class SingleLazyStandartTracker {

    private static Tracker instance;

    private SingleLazyStandartTracker() {
    }

    public static Tracker getInstance() {
        if (instance == null) {
            instance = new Tracker();
        }
        return instance;
    }
}
