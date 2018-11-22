package usersapp;

import net.jcip.annotations.ThreadSafe;

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

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public User findById(UUID id) {
        return store.get(id);
    }
}
