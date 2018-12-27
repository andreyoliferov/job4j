package usersapp;

import usersapp.items.Client;
import usersapp.items.Role;
import usersapp.items.User;
import usersapp.items.address.Address;
import usersapp.items.address.City;
import usersapp.items.address.Country;

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
    private final Store store = DBStore.getInstance();

    private ValidateService() {
    }

    public static Validate getInstance() {
        return ourInstance;
    }

    /**
     * Добавить пользователя
     * @param parameters параметры, переданные в запросе
     * @return id нового пользователя
     * @throws UserException некорректный email/пользователь с id уже существует
     */
    @Override
    public UUID add(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "name", "login", "email", "role");
        UUID roleId = validateId(parameters.get("role")[0], "role");
        Role role = findRoleById(roleId);
        User user = new User(
                parameters.get("name")[0],
                parameters.get("login")[0],
                parameters.get("password")[0],
                parameters.get("email")[0],
                role
        );
        this.validateLogin(user.getLogin());
        this.validateEmail(user.getEmail());
        this.validatePassword(user.getPassword());
        this.userIsNotExist(user.getId());
        this.loginIsNotExist(user.getLogin());
        return store.add(user);
    }

    /**
     * Обновить пользователя
     * @param parameters параметры, переданные в запросе
     * @return обновленный пользователь
     * @throws UserException нет обязательных параметров/некорректный id/переданы пустые параметры
     */
    @Override
    public User update(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "id");
        notEmptyOptionalParameters(parameters, "name", "login", "password", "email", "role");
        UUID id = validateId(parameters.get("id")[0], "user");
        User user = userIsExist(id);
        if (parameters.containsKey("name") && !parameters.get("name")[0].equals(user.getName())) {
            user.setName(parameters.get("name")[0]);
        }
        if (parameters.containsKey("login") && !parameters.get("login")[0].equals(user.getLogin())) {
            String login = parameters.get("login")[0];
            loginIsNotExist(login);
            validateLogin(login);
            user.setLogin(login);
        }
        if (parameters.containsKey("password") && !parameters.get("password")[0].equals(user.getPassword())) {
            String password = parameters.get("password")[0];
            validatePassword(password);
            user.setPassword(password);
        }
        if (parameters.containsKey("email") && !parameters.get("email")[0].equals(user.getEmail())) {
            String email = parameters.get("email")[0];
            validateEmail(email);
            user.setEmail(email);
        }
        if (parameters.containsKey("role") && !parameters.get("role")[0].equals(user.getRole().getId().toString())) {
            UUID roleId = validateId(parameters.get("role")[0], "role");
            user.setRole(findRoleById(roleId));
        }
        if (store.update(user) == null) {
            throw new UserException("error");
        }
        return user;
    }

    /**
     * Удаление пользователя
     * @param parameters параметры, переданные в запросе
     * @return удаленный пользователь
     * @throws UserException нет обязательных параметров/некорректный id
     */
    @Override
    public User delete(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "id");
        UUID id = validateId(parameters.get("id")[0], "user");
        this.userIsExist(id);
        return store.delete(id);
    }

    /**
     * Возвращает список всех пользователей
     * @throws UserException нет пользователей
     */
    @Override
    public List<User> findAll() throws UserException {
        List<User> temp = store.findAll();
        if (temp.size() == 0) {
            throw new UserException("User list is empty");
        }
        return temp;
    }

    /**
     * @return все роли в системе
     */
    public List<Role> findAllRoles() throws UserException {
        List<Role> temp = store.findAllRoles();
        if (temp.size() == 0) {
            throw new UserException("Role list is empty");
        }
        return temp;
    }

    /**
     * @return роль пользователя с id
     */
    public Role findRoleById(UUID id) {
        Role temp = store.findRoleById(id);
        if (temp == null) {
            throw new UserException("Role not found");
        }
        return temp;
    }

    /**
     * Найти пользователя по id
     * @param parameters параметры, епреданные в запросе
     * @return копия пользователя
     * @throws UserException невалидный id
     */
    @Override
    public User findById(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "id");
        UUID id = validateId(parameters.get("id")[0], "user");
        return userIsExist(id);
    }

    /**
     * @return список клиентов
     */
    @Override
    public List<Client> getClients(UUID user) {
        return store.getClients(user);
    }

    /**
     * @return список стран
     */
    @Override
    public List<Country> getCountries() {
        return store.getCountries();
    }

    public List<City> getCitiesOfCountry(UUID id) {
        return store.getCitiesOfCountry(id);
    }

    /**
     * Добавить клиента
     */
    @Override
    public UUID addClient(Client client) {
        UUID result;
        Address address = client.getAddress();
        UUID idAddress = store.findAddress(address.getCountry().getId(), address.getCity().getId(), address.getData());
        if (idAddress == null) {
            address.setId(UUID.randomUUID());
            idAddress = store.addAddress(address);
        } else {
            address.setId(idAddress);
        }
        if (idAddress == null) {
            throw new RuntimeException("creation client address error");
        }
        if (!store.addClient(client)) {
            throw new RuntimeException("creation client error");
        }
        result = client.getId();
        return result;
    }

    /**
     * Валидация email
     * @param email email
     * @throws UserException невалидный email
     */
    private void validateEmail(String email) throws UserException {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if (!pattern.matcher(email).matches()) {
            throw new UserException("Invalid email");
        }
    }

    /**
     * Валидация пароля
     * @param password пароль
     * @throws UserException некорректный пароль
     */
    private void validatePassword(String password) throws UserException {
        Pattern pattern = Pattern.compile(".*");
        if (!pattern.matcher(password).matches()) {
            throw new UserException("Incorrect password");
        }
    }

    /**
     * Валидация пароля
     * @param login логин
     * @throws UserException некорректный логин/логин уже существует
     */
    private void validateLogin(String login) throws UserException {
        Pattern pattern = Pattern.compile(".*");
        if (!pattern.matcher(login).matches()) {
            throw new UserException("Incorrect login");
        }
        if (false) {
            throw new UserException("Login is exist");
        }
    }

    /**
     * Проверка пользователя на существование в базе
     * @param id id
     * @return существующий пользователь
     * @throws UserException пользователь не существует
     */
    private User userIsExist(UUID id) throws UserException {
        User temp = store.findById(id);
        if (temp == null) {
            throw new UserException("User does not exist");
        }
        return temp;
    }

    /**
     * Проверка - пользователь не существует
     * @param id id
     * @throws UserException пользователь существует в базе
     */
    private void userIsNotExist(UUID id) throws UserException {
        if (store.findById(id) != null) {
            throw new UserException("User already exists");
        }
    }

    /**
     * Проверка существования логина
     */
    private void loginIsNotExist(String login) throws UserException {
        if (store.findByLogin(login) != null) {
            throw new UserException("Login already exists");
        }
    }

    /**
     * Проверка на обязательные поля
     * Поле должно присутствовать в запросе и не быть пустым
     * @param value параметры запроса
     * @param keys поля
     * @throws UserException отсутствуют обязательные поля
     */
    private void hasRequiredParameters(Map<String, String[]> value, String ... keys) throws UserException {
        for (String key : keys) {
            if (!value.containsKey(key) || value.get(key)[0].isEmpty()) {
                throw new UserException(String.format("incorrect request, has no parameter [%s]", key));
            }
        }
    }

    /**
     * Проверка на необязательные поля
     * Если поля присутствуют, они не должны быть пустые
     * @param value параметры запроса
     * @param keys поля
     * @throws UserException присутствуют пустые поля
     */
    private void notEmptyOptionalParameters(Map<String, String[]> value, String ... keys) throws UserException {
        for (String key : keys) {
            if (value.containsKey(key) && value.get(key)[0].isEmpty()) {
                throw new UserException(String.format("incorrect request, empty parameter [%s]", key));
            }
        }
    }

    /**
     * Валидация id
     * @param str id
     * @return UUID прошедший валидацию
     * @throws UserException невалидный id
     */
    private UUID validateId(String str, String field) throws UserException {
        UUID id;
        try {
            id = UUID.fromString(str);
        } catch (IllegalArgumentException e) {
            throw new UserException(String.format("%s [%s]", e.getMessage(), field));
        }
        return id;
    }

    /**
     * Запрос авторизации
     */
    public User auth(String login, String password) {
        return store.auth(login, password);
    }
}
