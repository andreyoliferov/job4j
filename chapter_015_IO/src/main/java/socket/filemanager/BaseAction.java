package socket.filemanager;

/**
 * @autor aoliferov
 * @since 31.01.2019
 */
public abstract class BaseAction implements IActions {

    private String key;
    private String display;

    public BaseAction(String key, String display) {
        this.key = key;
        this.display = display;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDisplay() {
        return display;
    }
}
