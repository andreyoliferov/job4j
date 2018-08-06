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
    private double weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children
                && Objects.equals(name, user.name)
                && Objects.equals(birthday, user.birthday);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(name, children, birthday);
//    }

    @Override
    public int hashCode() {
        int result = 31;
        result = 31 * result + name.hashCode();
        result = 31 * result + children;
        result = 31 * result + birthday.hashCode();
        long temp = Double.doubleToLongBits(weight);
        result =  31 * result + (int) (temp ^ (temp >>> 32));
        return result;
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
