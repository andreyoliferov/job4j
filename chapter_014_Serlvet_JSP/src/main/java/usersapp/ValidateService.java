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
    private final Store store = DBStore.getInstance();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
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

    /**
     * Обновить пользователя
     * @param parameters параметры, переданные в запросе
     * @return обновленный пользователь
     * @throws UserException нет обязательных параметров/некорректный id/переданы пустые параметры
     */
    @Override
    public User update(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "id");
        notEmptyOptionalParameters(parameters, "name", "login", "email");
        UUID id = validateId(parameters.get("id")[0]);
        User user = userIsExist(id);
        if (parameters.containsKey("name")) {
            user.setName(parameters.get("name")[0]);
        }
        if (parameters.containsKey("login")) {
            user.setLogin(parameters.get("login")[0]);
        }
        if (parameters.containsKey("email")) {
            String email = parameters.get("email")[0];
            validateEmail(email);
            user.setEmail(email);
        }
        store.update(user);
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
        UUID id = validateId(parameters.get("id")[0]);
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
     * Найти пользователя по id
     * @param parameters параметры, епреданные в запросе
     * @return копия пользователя
     * @throws UserException невалидный id
     */
    @Override
    public User findById(Map<String, String[]> parameters) throws UserException {
        hasRequiredParameters(parameters, "id");
        UUID id = validateId(parameters.get("id")[0]);
        return userIsExist(id);
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
    private UUID validateId(String str) throws UserException {
        UUID id;
        try {
            id = UUID.fromString(str);
        } catch (IllegalArgumentException e) {
            throw new UserException(e.getMessage());
        }
        return id;
    }
}
