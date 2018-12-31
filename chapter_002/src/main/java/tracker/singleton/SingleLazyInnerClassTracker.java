package tracker.singleton;

import tracker.Tracker;

/**
 * @autor aoliferov
 * @since 31.12.2018
 */
public class SingleLazyInnerClassTracker {

    private SingleLazyInnerClassTracker() {
    }

    public static Tracker getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final Tracker INSTANCE = new Tracker();
    }

}
