package tracker;

/**
 * @autor Андрей Олиферов
 * @since 30.05.2018
 */
public abstract class BaseAction implements UserAction {

    private final int key;
    private final String name;

    public BaseAction(final int key, final String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int key() {
        return key;
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), name);
    }
}
