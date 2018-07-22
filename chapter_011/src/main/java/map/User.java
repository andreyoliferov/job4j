package map;

import java.util.Calendar;
import java.util.Objects;

/**
 * @autor Андрей
 * @since 22.07.2018
 */
public class User {

    public User(String name, int children, Calendar bithday) {
        this.name = name;
        this.children = children;
        this.birthday = bithday;
    }

    private String name;
    private int children;
    private Calendar birthday;

    @Override
    public int hashCode() {
        return Objects.hash(name, children, birthday);
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", children=" + children
                + ", birthday=" + birthday.getTime()
                + '}';
    }
}
