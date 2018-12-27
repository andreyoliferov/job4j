package usersapp;

import net.jcip.annotations.ThreadSafe;
import usersapp.items.Client;
import usersapp.items.Role;
import usersapp.items.Rule;
import usersapp.items.User;
import usersapp.items.address.Address;
import usersapp.items.address.City;
import usersapp.items.address.Country;

import java.util.ArrayList;
import java.util.HashMap;
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

    public static Store getInstance() {
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

    @Override
    public HashMap<String, List<Rule>> accessData() {
        return null;
    }

    @Override
    public List<Client> getClients(UUID user) {
        return null;
    }

    @Override
    public List<Country> getCountries() {
        return null;
    }

    @Override
    public List<City> getCitiesOfCountry(UUID id) {
        return null;
    }

    @Override
    public boolean addClient(Client client) {
        return false;
    }

    @Override
    public UUID findAddress(UUID country, UUID city, String data) {
        return null;
    }

    @Override
    public UUID addAddress(Address address) {
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

    @Override
    public User findByLogin(String login) {
        return null;
    }
}
