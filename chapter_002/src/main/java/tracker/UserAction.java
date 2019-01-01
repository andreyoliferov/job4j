package tracker;

import java.util.function.Consumer;

/**
 * @autor Андрей Олиферов
 * @since 26.05.2018
 */
public interface UserAction {

    int key();
    void execute(Input input, ITracker tracker, Consumer<String> output);
    String info();
}
