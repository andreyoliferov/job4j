package trackerDB;

/**
 * @autor Андрей Олиферов
 * @since 26.05.2018
 */
public interface UserAction {

    int key();
    void execute(Input input, Tracker tracker);
    String info();
}
