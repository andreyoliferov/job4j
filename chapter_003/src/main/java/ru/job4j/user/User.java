package ru.job4j.user;

import java.util.UUID;

/**
 * @autor Андрей
 * @since 30.06.2018
 */
public class User {

    public User(String name, String city) {
        this.name = name;
        this.city = city;
        this.id = UUID.randomUUID();
    }

    private UUID id;
    private String name;
    private String city;

    public UUID getID() {
        return this.id;
    }

}
