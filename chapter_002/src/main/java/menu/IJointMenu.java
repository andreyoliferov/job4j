package menu;

/**
 * Узел меню.
 * @autor aoliferov
 * @since 18.02.2019
 */
public interface IJointMenu extends IMenuItem {

    void addNested(IMenuItem ... items);
    IMenuItem find(String name);

}
