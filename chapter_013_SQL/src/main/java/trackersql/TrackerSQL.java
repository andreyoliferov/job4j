package trackersql;

import jdbc.SQLStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tracker.ITracker;
import tracker.Item;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
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
        try(Statement st = connection.createStatement()){
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
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public List<Item> findByName(String key) {
        return null;
    }

    @Override
    public Item findById(String id) {
        return null;
    }


    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        ITracker tr = new TrackerSQL();
    }
}
