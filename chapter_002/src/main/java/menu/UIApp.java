package menu;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @autor aoliferov
 * @since 17.02.2019
 */
public class UIApp {

    private IJointMenu menu;
    private Scanner in;
    private PrintStream out;

    public UIApp(IJointMenu menu, InputStream in, PrintStream out) {
        this.menu = menu;
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Отобразить меню.
     */
    public void show() {
        menu.show(out, "");
    }

    /**
     * Выбрать меню.
     */
    public void select() {
        String temp = in.nextLine();
        IMenuItem finded = menu.find(temp);
        if (finded != null) {
            finded.execute();
        } else {
            out.println("Incorrect command!");
        }
    }
}
