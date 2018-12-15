package usersapp;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usersapp.items.Role;
import usersapp.items.Rule;
import usersapp.items.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * @autor aoliferov
 * @since 02.12.2018
 */
public class DBStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(DBStore.class);
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
        User resultUser = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = SOURCE.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
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
}
