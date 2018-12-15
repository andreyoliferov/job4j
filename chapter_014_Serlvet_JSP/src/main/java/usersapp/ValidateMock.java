package usersapp;

import usersapp.items.Role;
import usersapp.items.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
public class ValidateMock implements Validate {

    private static ValidateMock ourInstance = new ValidateMock();

    private ValidateMock() {
    }

    public static Validate getInstance() {
        return ourInstance;
    }

    @Override
    public UUID add(Map<String, String[]> parameters) throws UserException {
        return null;
    }

    @Override
    public User update(Map<String, String[]> parameters) throws UserException {
        return null;
    }

    @Override
    public User delete(Map<String, String[]> parameters) throws UserException {
        return null;
    }

    @Override
    public List<User> findAll() throws UserException {
        return null;
    }

    public List<Role> findAllRoles() throws UserException {
        return null;
    }

    public Role findRoleById(UUID id) {
        return null;
    }

    @Override
    public User findById(Map<String, String[]> parameters) throws UserException {
        return null;
    }

    public User auth(String login, String password) {
        return null;
    }
}
