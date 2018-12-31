package tracker.singleton;

import tracker.Tracker;

/**
 * @autor aoliferov
 * @since 31.12.2018
 */
public class SingleEagerStandartTtacker {

    private static final Tracker INSTANCE = new Tracker();

    private SingleEagerStandartTtacker() {
    }

    public static Tracker getInstance() {
        return INSTANCE;
    }
}
