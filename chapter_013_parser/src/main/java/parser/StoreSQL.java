package parser;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static parser.HunterApp.LOG;

/**
 * @autor aoliferov
 * @since 12.11.2018
 */
public class StoreSQL implements AutoCloseable {


    /**
     * Поле - объект подключения
     */
    private Connection conn;
    private Properties prop;

    /**
     * Конструктор - создает подключение к БД. Создает таблицу, если она отсутствует.
     */
    public StoreSQL() {
        prop = new Properties();
        try (InputStream is = StoreSQL.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(is);
            Class.forName(prop.getProperty("driver-class-name"));
            conn = DriverManager.getConnection(prop.getProperty("url").concat(prop.getProperty("namedb")), prop);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            createDb();
        } catch (ClassNotFoundException | IOException  e) {
            LOG.error(e.getMessage());
        }
        createTable();
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }

    /**
     * Создание БД
     */
    private void createDb() {
        try (Connection newBase = DriverManager.getConnection(prop.getProperty("url"), prop);
             Statement st = newBase.createStatement()) {
            st.execute(String.format("CREATE database %s", prop.getProperty("namedb")));
            conn = DriverManager.getConnection(prop.getProperty("url")
                    .concat(prop.getProperty("namedb")), prop);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Создание таблицы
     */
    private void createTable() {
        try (Statement st = conn.createStatement()) {
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS vacancies (")
                            .append("id serial primary key,")
                            .append("name varchar(500),")
                            .append("author varchar(100),")
                            .append("link varchar(500),")
                            .append("date timestamp,")
                            .append("description text,")
                            .append("source varchar(50),")
                            .append("constraint un_name_auth unique(name, author, source))")
                            .toString()
            );
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }

    public int addVacancy(Vacancy v) {
        int id = -1;
        String sql =
                "INSERT INTO vacancies (name, author, link, date, description, source) VALUES (?, ?, ?, ?, ?, ?) " +
                "ON conflict ON CONSTRAINT un_name_auth " +
                "DO UPDATE SET link = excluded.link, date = excluded.date, description = excluded.description " +
                "WHERE vacancies.date < excluded.date";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getName());
            ps.setString(2, v.getAuthor());
            ps.setString(3, v.getLink());
            ps.setTimestamp(4, Timestamp.valueOf(v.getDate()));
            ps.setString(5, v.getDesc());
            ps.setString(6, v.getSource());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return id;
    }

    public LocalDateTime getLastDate(String source) {
        LocalDateTime last = LocalDateTime.now().minus(Period.ofYears(1)).truncatedTo(ChronoUnit.MINUTES);
        String sql = "SELECT MAX(date) as date FROM vacancies WHERE source = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, source);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    last = rs.getTimestamp("date").toLocalDateTime();
                }
            }
        } catch (SQLException | NullPointerException e) {
            LOG.error(e.getMessage());
        }
        return last;
    }
}
