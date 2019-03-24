package ru.oliferov.platform.user.service;

import ru.oliferov.platform.user.models.User;

import java.util.List;

/**
 * @autor aoliferov
 * @since 24.03.2019
 */
public interface UserService {

    User addUser(User user);
    void delete(int id);
    User getByName(String name);
    User getById(int id);
    User editUser(User user);
    List<User> getAll();

}
