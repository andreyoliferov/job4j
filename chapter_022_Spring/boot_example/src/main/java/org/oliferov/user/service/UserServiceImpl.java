package org.oliferov.user.service;

import org.oliferov.user.dao.UserRepository;
import org.oliferov.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @autor aoliferov
 * @since 24.03.2019
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User editUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
