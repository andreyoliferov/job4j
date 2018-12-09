package usersapp;

import usersapp.items.Role;
import usersapp.items.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
public interface Validate {

    UUID add(Map<String, String[]> parameters);

    User update(Map<String, String[]> parameters);

    User delete(Map<String, String[]> parameters);

    List<User> findAll();

    List<Role> findAllRoles();

    User findById(Map<String, String[]> parameters);

    User auth(String login, String password);

}
