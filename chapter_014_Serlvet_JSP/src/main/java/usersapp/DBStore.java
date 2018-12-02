package usersapp;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 02.12.2018
 */
public class DBStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(DBStore.class);
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static DBStore INSTANCE = new DBStore();
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
        prepareTable();
    }

    /**
     * Процедура создания таблицы
     */
    private void prepareTable() {
        try {
            Connection conn = SOURCE.getConnection();
            try (Statement st = conn.createStatement()) {
                st.execute(
                        new StringBuilder()
                                .append("CREATE TABLE IF NOT EXISTS users (")
                                .append("id uuid primary key,")
                                .append("name varchar(300),")
                                .append("login varchar(300),")
                                .append("email varchar(300),")
                                .append("date_created timestamp)")
                                .toString()
                );
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    @Override
    public UUID add(User user) {
        UUID result = user.getId();
        String sql = "INSERT INTO users (id, name, login, email, date_created) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = SOURCE.getConnection().prepareStatement(sql)) {
            ps.setObject(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getEmail());
            ps.setTimestamp(5, Timestamp.valueOf(user.getCreateDate()));
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
        String sql = "UPDATE users SET name = ?, login = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = SOURCE.getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setObject(3, user.getEmail());
            ps.setObject(4, user.getId());
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
        try (PreparedStatement ps = SOURCE.getConnection().prepareStatement(sql)) {
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
        try (Statement st = SOURCE.getConnection().createStatement()) {
            try (ResultSet result = st.executeQuery("SELECT * FROM users")) {
                while (result.next()) {
                    users.add(new User(
                            (UUID) result.getObject("id"),
                            result.getString("name"),
                            result.getString("login"),
                            result.getString("email"),
                            result.getTimestamp("date_created").toLocalDateTime()));
                }
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
        try (PreparedStatement ps = SOURCE.getConnection().prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    resultUser = new User((UUID) result.getObject("id"),
                            result.getString("name"),
                            result.getString("login"),
                            result.getString("email"),
                            result.getTimestamp("date_created").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return resultUser;
    }
}
