package ru.oliferov.storage.other;

import java.util.UUID;

/**
 * @autor aoliferov
 * @since 12.03.2019
 */
public class AppUser {

    private Storage storage;

    public AppUser(Storage storage) {
        this.storage = storage;
    }

    public UUID add(User user) {
        return storage.add(user);
    }

    public void delete(UUID id) {
        storage.delete(id);
    }
}
