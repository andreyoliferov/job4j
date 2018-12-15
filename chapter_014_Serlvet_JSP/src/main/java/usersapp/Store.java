package usersapp;

import usersapp.items.Role;
import usersapp.items.Rule;
import usersapp.items.User;

import java.util.HashMap;
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

    List<Role> findAllRoles();

    Role findRoleById(UUID id);

    User auth(String login, String password);

    HashMap<String, List<Rule>> accessData();

}
