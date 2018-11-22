package usersapp;

import java.util.List;
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
    public UUID add(User user) throws UserException {
        this.validateEmail(user.getEmail());
        this.userIsNotExist(user.getId());
        return store.add(user);
    }

    @Override
    public User update(User user) throws UserException {
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
    public User delete(UUID id) throws UserException {
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
    public User findById(UUID id) throws UserException {
        return userIsExist(id);
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
}
