package usersapp;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usersapp.items.*;
import usersapp.items.address.Address;
import usersapp.items.address.City;
import usersapp.items.address.Country;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * @autor aoliferov
 * @since 02.12.2018
 */
public class DBStore implements Store {

    private static final Logger LOG = LogManager.getLogger(DBStore.class);
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DBStore INSTANCE = new DBStore();
    private static Properties properties;

    public DBStore() {
        properties = new Properties();
        try (InputStream fis = DBStore.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SOURCE.setDriverClassName(properties.getProperty("driver-class-name"));
        SOURCE.setUrl(properties.getProperty("url").concat(properties.getProperty("namedb")));
        SOURCE.setUsername(properties.getProperty("user"));
        SOURCE.setPassword(properties.getProperty("password"));
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        try (Connection conn = SOURCE.getConnection()) {
            new CreaterDefaultData().prepareData(conn);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static Store getInstance() {
        return INSTANCE;
    }

    @Override
    public UUID add(User user) {
        UUID result = user.getId();
        String sql = "INSERT INTO users (id, name, login, password, email, role, date_created) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setObject(6, user.getRole().getId());
            ps.setTimestamp(7, Timestamp.valueOf(user.getCreateDate()));
            ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            result = null;
        }
        return result;
    }

    @Override
    public User update(User user) {
        User userOld = findById(user.getId());
        String sql = "UPDATE users SET name = ?, login = ?, password = ?, email = ?, role = ? WHERE id = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setObject(4, user.getEmail());
            ps.setObject(5, user.getRole().getId());
            ps.setObject(6, user.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            userOld = null;
        }
        return userOld;
    }

    @Override
    public User delete(UUID id) {
        User user = findById(id);
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            user = null;
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = SOURCE.getConnection();
             Statement st = conn.createStatement();
             ResultSet result = st.executeQuery("SELECT * FROM users")) {
            while (result.next()) {
                users.add(new User(
                        (UUID) result.getObject("id"),
                        result.getString("name"),
                        result.getString("login"),
                        result.getString("password"),
                        result.getString("email"),
                        findRoleById((UUID) result.getObject("role")),
                        result.getTimestamp("date_created").toLocalDateTime()));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            users = null;
        }
        return users;
    }

    @Override
    public User findById(UUID id) {
        return findByData("id", id);
    }

    @Override
    public User findByLogin(String login) {
        return findByData("login", login);
    }

    private User findByData(String name, Object object) {
        User resultUser = null;
        String sql = String.format("SELECT * FROM users WHERE %s = ?", name);
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, object);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    resultUser = new User((UUID) result.getObject("id"),
                            result.getString("name"),
                            result.getString("login"),
                            result.getString("password"),
                            result.getString("email"),
                            findRoleById((UUID) result.getObject("role")),
                            result.getTimestamp("date_created").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return resultUser;
    }

    public List<Role> findAllRoles() {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = SOURCE.getConnection();
             Statement st = conn.createStatement();
             ResultSet result = st.executeQuery("SELECT * FROM roles")) {
            while (result.next()) {
                UUID idRole = (UUID) result.getObject("id");
                roles.add(new Role(
                        idRole,
                        result.getString("name"),
                        findRulesByRoleId(idRole)
                ));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            roles = null;
        }
        return roles;
    }

    public Role findRoleById(UUID id) {
        Role resultRole = null;
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    resultRole = new Role(
                            id,
                            result.getString("name"),
                            findRulesByRoleId(id)
                    );
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return resultRole;
    }

    public List<Rule> findRulesByRoleId(UUID roleId) {
        List<Rule> rules = new ArrayList<>();
        String sql = "SELECT r.id, r.name FROM rules as r "
                + "INNER JOIN roles_rules as rr ON rr.rule = r.id WHERE rr.role = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, roleId);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    rules.add(
                            new Rule((UUID) result.getObject("id"), result.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rules;
    }

    public User auth(String login, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, password);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    user = new User((UUID) result.getObject("id"),
                            result.getString("name"),
                            result.getString("login"),
                            result.getString("login"),
                            result.getString("email"),
                            findRoleById((UUID) result.getObject("role")),
                            result.getTimestamp("date_created").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public HashMap<String, List<Rule>> accessData() {
        HashMap<String, List<Rule>> result = new HashMap<>();
        try (Connection conn = SOURCE.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT a.path, a.rule, r.name FROM access AS a "
                + "LEFT OUTER JOIN rules AS r ON a.rule = r.id")) {
            while (rs.next()) {
                String path = rs.getString(1);
                Rule rule = new Rule((UUID) rs.getObject(2), rs.getString(3));
                if (!result.containsKey(path)) {
                    result.put(path, new ArrayList<>(Arrays.asList(rule)));
                } else {
                    result.get(path).add(rule);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * @return список клиентов
     */
    @Override
    public List<Client> getClients(UUID user) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE owner = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, user);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    clients.add(new Client(
                            (UUID) result.getObject("id"),
                            result.getString("first_name"),
                            result.getString("second_name"),
                            new Contact(
                                    result.getString("phone"),
                                    result.getString("email"),
                                    findAddressById((UUID) result.getObject("address"))),
                            result.getString("sex"),
                            result.getTimestamp("date_created").toLocalDateTime(),
                            (UUID) result.getObject("owner")));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            clients = null;
        }
        return clients;
    }

    /**
     * @return адрес с id
     */
    public Address findAddressById(UUID id) {
        Address address = null;
        String sql = "SELECT * FROM addresses WHERE id = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    address = new Address(
                            id,
                            getCountry((UUID) result.getObject("country")),
                            getCity((UUID) result.getObject("city")),
                            result.getString("data")
                    );
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return address;
    }

    /**
     * @return адрес с id
     */
    public UUID findAddress(UUID country, UUID city, String data) {
        UUID address = null;
        String sql = "SELECT id FROM addresses WHERE country = ? AND city = ? AND data = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, country);
            ps.setObject(2, city);
            ps.setString(3, data);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    address = (UUID) result.getObject("id");
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return address;
    }

    public UUID addAddress(Address address) {
        UUID result = address.getId();
        String sql = "INSERT INTO addresses (id, country, city, data) VALUES (?, ?, ?, ?)";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, address.getId());
            ps.setObject(2, address.getCountry().getId());
            ps.setObject(3, address.getCity().getId());
            ps.setString(4, address.getData());
            ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            result = null;
        }
        return result;
    }

    /**
     * @return страну с id
     */
    public Country getCountry(UUID id) {
        Country country = null;
        String sql = "SELECT * FROM countries WHERE id = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    country = new Country(
                            id,
                            result.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return country;
    }

    /**
     * @return список стран
     */
    @Override
    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();
        try (Connection conn = SOURCE.getConnection();
             Statement st = conn.createStatement();
             ResultSet result = st.executeQuery("SELECT * FROM countries")) {
            while (result.next()) {
                UUID id = (UUID) result.getObject("id");
                countries.add(new Country(
                        id,
                        result.getString("name")
                ));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            countries = null;
        }
        return countries;
    }

    /**
     * @return список городов для страны
     */
    public List<City> getCitiesOfCountry(UUID id) {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM cities WHERE country = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    cities.add(
                            new City(
                                    (UUID) result.getObject("id"),
                                    result.getString("name")
                            ));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            cities = null;
        }
        return cities;
    }

    /**
     * @return город с id
     */
    public City getCity(UUID id) {
        City city = null;
        String sql = "SELECT * FROM cities WHERE id = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    city = new City(
                            id,
                            result.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return city;
    }

    /**
     * Добавить клиента в систему
     * @return результат
     */
    @Override
    public boolean addClient(Client client) {
        boolean result = true;
        String sql = "INSERT INTO clients (id, first_name, second_name, phone, email, sex, address, date_created, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, client.getId());
            ps.setString(2, client.getFirstName());
            ps.setString(3, client.getSecondName());
            ps.setString(4, client.getPhone());
            ps.setString(5, client.getEmail());
            ps.setObject(6, client.getSex());
            ps.setObject(7, client.getAddress().getId());
            ps.setTimestamp(8, Timestamp.valueOf(client.getCreateDate()));
            ps.setObject(9, client.getOwner());
            ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            result = false;
        }
        return result;
    }
}
