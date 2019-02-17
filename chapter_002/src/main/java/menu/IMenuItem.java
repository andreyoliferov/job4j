package menu;

import java.io.OutputStream;

/**
 * Пункт меню.
 * Не предполагает АПИ поиска и добавления пунктов.
 * @autor aoliferov
 * @since 17.02.2019
 */
public interface IMenuItem {

    void show(OutputStream out, String outset);
    String execute();

}
