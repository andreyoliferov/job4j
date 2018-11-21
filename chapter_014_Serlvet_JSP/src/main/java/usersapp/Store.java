package usersapp;

import java.util.List;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
public interface Store {

    UUID add(User user);

    User update(User user);

    User delete(UUID id);

    List<User> findAll();

    User findById(UUID id);
}
