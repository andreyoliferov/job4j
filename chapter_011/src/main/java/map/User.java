package map;

import java.util.Calendar;

/**
 * @autor Андрей
 * @since 22.07.2018
 */
public class User {

    public User(String name, int children, Calendar bithday){
        this.name = name;
        this.children = children;
        this.birthday = bithday;
    }

    private String name;
    private int children;
    private Calendar birthday;


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", children=" + children +
                ", birthday=" + birthday.getTime() +
                '}';
    }
}
