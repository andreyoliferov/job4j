package tracker.nosql.mongo;

import tracker.ConsoleInput;
import tracker.StartUI;
import tracker.ValidateInput;

/**
 * @autor aoliferov
 * @since 01.09.2019
 */
public class StartMongo {

    public static void main(String[] args) {
        try (MongoTracker tracker = new MongoTracker()) {
            new StartUI(new ValidateInput(new ConsoleInput()), tracker, System.out::println).init();
        }
    }
}
