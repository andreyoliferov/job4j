package usersapp;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
public class ValidateService implements Validate {

    private static ValidateService ourInstance = new ValidateService();
    private final Store store = MemoryStore.getInstance();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return ourInstance;
    }

    @Override
    public UUID add(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "name", "login", "email");
        User user = new User(
                parameters.get("name")[0],
                parameters.get("login")[0],
                parameters.get("email")[0]
        );
        this.validateEmail(user.getEmail());
        this.userIsNotExist(user.getId());
        return store.add(user);
    }

    @Override
    public User update(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "id");
        User user = new User(
                UUID.fromString(parameters.get("id")[0]),
                getOptionalParameter(parameters, "name"),
                getOptionalParameter(parameters, "login"),
                getOptionalParameter(parameters, "email")
        );
        User old = this.userIsExist(user.getId());
        if (user.getName() == null) {
            user.setName(old.getName());
        }
        if (user.getLogin() == null) {
            user.setLogin(old.getLogin());
        }
        if (user.getEmail() == null) {
            user.setEmail(old.getEmail());
        }
        this.validateEmail(user.getEmail());
        return store.update(user);
    }

    @Override
    public User delete(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "id");
        UUID id = UUID.fromString(parameters.get("id")[0]);
        this.userIsExist(id);
        return store.delete(id);
    }

    @Override
    public List<User> findAll() throws UserException {
        List<User> temp = store.findAll();
        if (temp.size() == 0) {
            throw new UserException("User list is empty");
        }
        return temp;
    }

    @Override
    public User findById(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "id");
        return userIsExist(UUID.fromString(parameters.get("id")[0]));
    }

    private void validateEmail(String email) throws UserException {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if (!pattern.matcher(email).matches()) {
            throw new UserException("Invalid email");
        }
    }

    private User userIsExist(UUID id) throws UserException {
        User temp = store.findById(id);
        if (store.findById(id) == null) {
            throw new UserException("User does not exist");
        }
        return temp;
    }

    private void userIsNotExist(UUID id) throws UserException {
        if (store.findById(id) != null) {
            throw new UserException("User already exists");
        }
    }

    private String getOptionalParameter(Map<String, String[]> value, String key) {
        return value.containsKey(key) ? value.get(key)[0] : null;
    }

    private void hasRequiredParameters(Map value, String ... keys) {
        for (String key : keys) {
            if (!value.containsKey(key)) {
                throw new UserException(String.format("incorrect request, has no parameter [%s]", key));
            }
        }
    }
}
