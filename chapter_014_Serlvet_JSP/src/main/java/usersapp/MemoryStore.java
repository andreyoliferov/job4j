package usersapp;

import net.jcip.annotations.ThreadSafe;
import usersapp.items.Role;
import usersapp.items.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
@ThreadSafe
public class MemoryStore implements Store {

    private static MemoryStore ourInstance = new MemoryStore();
    private final ConcurrentHashMap<UUID, User> store = new ConcurrentHashMap<>();

    private MemoryStore() {
    }

    public static MemoryStore getInstance() {
        return ourInstance;
    }

    @Override
    public UUID add(User user) {
        UUID id = user.getId();
        store.put(id, user);
        return id;
    }

    @Override
    public User update(User user) {
        return store.replace(user.getId(), user);
    }

    @Override
    public User delete(UUID id) {
        return store.remove(id);
    }

    /**
     * @return лист пользователей
     */
    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Role> findAllRoles() {
        return null;
    }

    @Override
    public Role findRoleById(UUID id) {
        return null;
    }

    @Override
    public User auth(String login, String password) {
        return null;
    }

    /**
     * Поиск пользователя по id
     * @param id id
     * @return копия существующего пользователя, null если не сущетсвует
     */
    @Override
    public User findById(UUID id) {
        User result = null;
        User temp = store.get(id);
        if (temp != null) {
            result = new User(temp);
        }
        return result;
    }
}
