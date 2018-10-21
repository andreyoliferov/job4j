package trackersql;

import jdbc.SQLStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tracker.ITracker;
import tracker.Item;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Реализация трекера с хранением заявок в PostgresSQL
 * @autor aoliferov
 * @since 17.10.2018
 */

public class TrackerSQL implements ITracker, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(SQLStorage.class);
    private Connection connection;
    private Properties prop;

    public TrackerSQL() throws ClassNotFoundException, SQLException, IOException {
        prop = new Properties();
        try (FileInputStream fis =
                     new FileInputStream("chapter_013_SQL/src/main/resources/configTracker.properties")) {
            prop.load(fis);
        }
        Class.forName(prop.getProperty("driver-class-name"));
        try {
            connection = DriverManager.getConnection(prop.getProperty("url").concat(prop.getProperty("namedb")), prop);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            createDb();
        }
        createTable();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    /**
     * Создание БД
     */
    private void createDb() throws SQLException {
        try (Connection newBase = DriverManager.getConnection(prop.getProperty("url"), prop);
             Statement st = newBase.createStatement()) {
            st.execute(String.format("CREATE database %s", prop.getProperty("namedb")));
            connection = DriverManager.getConnection(prop.getProperty("url")
                    .concat(prop.getProperty("namedb")), prop);
        }
    }

    /**
     * Создание таблицы
     */
    private void createTable() throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS orders (")
                            .append("id uuid primary key,")
                            .append("name varchar(300),")
                            .append("description text,")
                            .append("date_created timestamp)")
                            .toString()
            );
        }
    }

    @Override
    public Item add(Item item) {
        String sql = "INSERT INTO orders (id, name, description, date_created) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, UUID.fromString(item.getId()));
            ps.setString(2, item.getName());
            ps.setString(3, item.getDesc());
            ps.setTimestamp(4, Timestamp.valueOf(item.getCreatedLocalDate()));
            ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        String sql = "UPDATE orders SET name = ?, description = ?, date_created = date_trunc('second', current_timestamp) WHERE id = ?";
        boolean result = false;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDesc());
            ps.setObject(3, UUID.fromString(id));
            result = ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        int result = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, UUID.fromString(id));
            result = ps.executeUpdate();
        } catch (SQLException | IllegalArgumentException e) {
            LOG.error(e.getMessage());
        }
        return result != 0;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            try (ResultSet result = st.executeQuery("SELECT * FROM orders")) {
                while (result.next()) {
                    items.add(new Item(result.getObject("id").toString(),
                            result.getString("name"),
                            result.getString("description"),
                            result.getTimestamp("date_created").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, key);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    items.add(new Item(result.getObject("id").toString(),
                            result.getString("name"),
                            result.getString("description"),
                            result.getTimestamp("date_created").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return items;
    }

    @Override
    public Item findById(String id) {
        Item old = null;
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, UUID.fromString(id));
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    old = new Item(result.getObject("id").toString(), result.getString("name"),
                            result.getString("description"), result.getTimestamp("date_created").toLocalDateTime());
                }
            }
        } catch (SQLException | IllegalArgumentException e) {
            LOG.error(e.getMessage());
        }
        return old;
    }
}
