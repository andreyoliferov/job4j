package ru.job4j.user;

import java.util.UUID;

/**
 * @autor Андрей
 * @since 30.06.2018
 */
public class User implements Comparable<User> {

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", age=" + age
                + ", city='" + city + '\''
                + '}';
    }

    public User(String name, Integer age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.id = UUID.randomUUID();
    }

    private UUID id;
    private String name;
    private Integer age;
    private String city;

    public UUID getID() {
        return this.id;
    }

    @Override
    public int compareTo(User o) {
        int result = this.age.compareTo(o.age);
        if (result == 0) {
            result = this.name.compareTo(o.name);
        }
        return result;
    }
}
