package util;

import usersapp.UserException;
import usersapp.Validate;
import usersapp.items.Role;
import usersapp.items.Rule;
import usersapp.items.User;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
public class ValidateMock implements Validate {

    private static ValidateMock ourInstance = new ValidateMock();
    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, Role> roles = new HashMap<>();
    private HashMap<String, Rule> rules = new HashMap<>();

    public ValidateMock() {
        rules.put("49c50cbd-93c8-4fad-b7bb-91a63321767b",
                new Rule(UUID.fromString("49c50cbd-93c8-4fad-b7bb-91a63321767b"), "view"));
        rules.put("622f3bf4-dac7-4e9e-9d47-29fef0e25409",
                new Rule(UUID.fromString("622f3bf4-dac7-4e9e-9d47-29fef0e25409"), "create"));
        rules.put("4052cdea-f091-4934-80a5-51bc42eff90c",
                new Rule(UUID.fromString("4052cdea-f091-4934-80a5-51bc42eff90c"), "update"));

        roles.put("09888872-58a9-40aa-935f-b0a56dba7fff",
                new Role(
                        UUID.fromString("09888872-58a9-40aa-935f-b0a56dba7fff"),
                        "administrator",
                        Arrays.asList(
                                rules.get("49c50cbd-93c8-4fad-b7bb-91a63321767b"),
                                rules.get("622f3bf4-dac7-4e9e-9d47-29fef0e25409"),
                                rules.get("4052cdea-f091-4934-80a5-51bc42eff90c")
                        )
                )
        );
        roles.put("7b642c55-7b3f-4e8c-964a-e8b5a5286ec2",
                new Role(
                        UUID.fromString("7b642c55-7b3f-4e8c-964a-e8b5a5286ec2"),
                        "guest",
                        Arrays.asList(rules.get("49c50cbd-93c8-4fad-b7bb-91a63321767b"))
                )
        );
        users.put("544bbba5-25e4-4b81-9384-04734ac61d48", new User(UUID.fromString("544bbba5-25e4-4b81-9384-04734ac61d48"),
                "administratorName",
                "administratorLogin",
                "administratorPassword",
                "administrator@mail.ru",
                roles.get("09888872-58a9-40aa-935f-b0a56dba7fff"),
                LocalDateTime.of(2018, 12, 15, 0, 0)));
        users.put("2b60e511-0c66-437e-976f-fab1def3d1a3", new User(UUID.fromString("2b60e511-0c66-437e-976f-fab1def3d1a3"),
                "guestName",
                "guestLogin",
                "guestPassword",
                "guest@mail.ru",
                roles.get("7b642c55-7b3f-4e8c-964a-e8b5a5286ec2"),
                LocalDateTime.of(2018, 12, 16, 0, 0)));
    }

    public static Validate getInstance() {
        return ourInstance;
    }

    @Override
    public UUID add(Map<String, String[]> parameters) throws UserException {
        UUID roleId = UUID.fromString(parameters.get("role")[0]);
        Role role = findRoleById(roleId);
        String[] error = parameters.get("exception");
        if (error != null) {
            throw new UserException(error[0]);
        }
        User user = new User(
                parameters.get("name")[0],
                parameters.get("login")[0],
                parameters.get("password")[0],
                parameters.get("email")[0],
                role
        );
        UUID id = user.getId();
        users.put(id.toString(), user);
        return id;
    }

    @Override
    public User update(Map<String, String[]> parameters) throws UserException {
        String[] error = parameters.get("exception");
        if (error != null) {
            throw new UserException(error[0]);
        }
        String id = parameters.get("id")[0];
        UUID roleId =  UUID.fromString(parameters.get("role")[0]);
        User user = userIsExist(UUID.fromString(id));
        user.setName(parameters.get("name")[0]);
        user.setLogin(parameters.get("login")[0]);
        user.setPassword(parameters.get("password")[0]);
        user.setEmail(parameters.get("email")[0]);
        user.setRole(findRoleById(roleId));
        users.put(id, user);
        return user;
    }

    @Override
    public User delete(Map<String, String[]> parameters) throws UserException {
        return null;
    }

    @Override
    public List<User> findAll() throws UserException {
        return new ArrayList<>(users.values());
    }

    public List<Role> findAllRoles() throws UserException {
        return null;
    }

    public Role findRoleById(UUID id) {
        return roles.get(id.toString());
    }

    @Override
    public User findById(Map<String, String[]> parameters) throws UserException {
        return userIsExist(UUID.fromString(parameters.get("id")[0]));
    }

    public User auth(String login, String password) {
        User result = null;
        for (Map.Entry<String, User> user : users.entrySet()) {
            if (user.getValue().getLogin().equals(login) && user.getValue().getPassword().equals(password)) {
                result = user.getValue();
                break;
            }
        }
        return result;
    }

    private User userIsExist(UUID id) throws UserException {
        User temp = users.get(id.toString());
        if (temp == null) {
            throw new UserException("User does not exist");
        }
        return temp;
    }
}
