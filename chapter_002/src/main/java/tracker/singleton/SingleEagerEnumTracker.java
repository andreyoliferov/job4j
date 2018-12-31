package tracker.singleton;

import tracker.Tracker;

/**
 * @autor aoliferov
 * @since 31.12.2018
 */
public enum SingleEagerEnumTracker {

    INSTANCE;

    private Tracker tracker;

    SingleEagerEnumTracker() {
        this.tracker = new Tracker();
    }

    public static Tracker getInstanse() {
        return INSTANCE.tracker;
    }

}
