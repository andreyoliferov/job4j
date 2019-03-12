package ru.oliferov.storage.other;

import java.util.UUID;

/**
 * @autor aoliferov
 * @since 12.03.2019
 */
public interface Storage {

    UUID add(User user);
    void delete(UUID id);

}
