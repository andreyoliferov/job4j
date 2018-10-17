package tracker;

/**
 * @autor Андрей Олиферов
 * @since 26.05.2018
 */
public interface UserAction {

    int key();
    void execute(Input input, ITracker tracker);
    String info();
}
