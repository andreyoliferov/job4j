package menu;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Реализация пункта меню.
 * @autor aoliferov
 * @since 17.02.2019
 */
public class MenuItem implements IJointMenu {

    private String name;
    private List<IMenuItem>  nested;
    private Supplier<String> supplier;

    public MenuItem(String name, Supplier<String> supplier) {
        this.name = name;
    }

    public MenuItem(String name, List<IMenuItem> nested, Supplier<String> supplier) {
        this.name = name;
        this.nested = nested;
        this.supplier = supplier;
    }

    @Override
    public void addNested(IMenuItem ... iMenuItem) {
        if (nested == null) {
            nested = new ArrayList<>();
        }
        nested.addAll(List.of(iMenuItem));
    }

    @Override
    public IMenuItem find(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        IMenuItem result = null;
        int i = 0;
        while (result == null && nested != null && i < nested.size()) {
            result = ((IJointMenu) nested.get(i++)).find(name);
        }
        return result;
    }

    @Override
    public String execute() {
        return this.supplier.get();
    }

    @Override
    public void show(OutputStream out, String outset) {
        PrintStream ps = new PrintStream(out);
        ps.println(outset.concat(name));
        if (nested != null && nested.size() != 0) {
            nested.forEach((item) -> item.show(out, outset.concat("-")));
        }
    }
}
