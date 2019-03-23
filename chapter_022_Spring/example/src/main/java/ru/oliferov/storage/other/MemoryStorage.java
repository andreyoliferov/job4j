package ru.oliferov.storage.other;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 12.03.2019
 */
@Component("memStorage")
@Scope("singleton")
public class MemoryStorage implements Storage {

    private HashMap<UUID, User> data = new HashMap<>();

    @Override
    public UUID add(User user) {
        UUID id = UUID.randomUUID();
        data.put(id, user);
        return id;
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
