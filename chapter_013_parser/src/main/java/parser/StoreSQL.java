package parser;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static parser.HunterApp.LOG;
import static parser.HunterApp.properties;

/**
 * @autor aoliferov
 * @since 12.11.2018
 */
public class StoreSQL implements AutoCloseable {

    /**
     * Поле - объект подключения
     */
    private Connection conn;

    /**
     * Конструктор - создает подключение к БД. Создает бд и таблицу, если они отсутствуют.
     */
    public StoreSQL() {
        try {
            Class.forName(properties.getProperty("driver-class-name"));
            conn = DriverManager.getConnection(properties.getProperty("url").concat(properties.getProperty("namedb")), properties);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            createDb();
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
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
        try (Connection newBase = DriverManager.getConnection(properties.getProperty("url"), properties);
             Statement st = newBase.createStatement()) {
            st.execute(String.format("CREATE database %s", properties.getProperty("namedb")));
            conn = DriverManager.getConnection(properties.getProperty("url")
                    .concat(properties.getProperty("namedb")), properties);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
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
                            .append("author varchar(300),")
                            .append("link varchar(500),")
                            .append("date timestamp,")
                            .append("description text,")
                            .append("source varchar(50),")
                            .append("constraint un_name_auth unique(name, author, source))")
                            .toString()
            );
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Метод добавляет вакансию в БД
     * если вакансия существует (constraint un_name_auth) и vacancies.date < excluded.date
     * поля link, description, date обновляются
     * @param v вакансия
     * @return
     */
    public int addVacancy(Vacancy v) {
        int id = -1;
        String sql = "INSERT INTO vacancies (name, author, link, date, description, source) VALUES (?, ?, ?, ?, ?, ?) "
                + "ON conflict ON CONSTRAINT un_name_auth "
                + "DO UPDATE SET link = excluded.link, date = excluded.date, description = excluded.description "
                + "WHERE vacancies.date < excluded.date";
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
            LOG.error(e.getMessage(), e);
        }
        return id;
    }

    /**
     * Метод возвращает актуальную дату для начала поиска вакансий
     * @param source ресурс, где расположена вакансия
     * @return дата последней записи для ресурса
     */
    public LocalDateTime getLastDate(String source) {
        LocalDateTime last = LocalDateTime.now().minus(Period.ofYears(1)).truncatedTo(ChronoUnit.MINUTES);
        String sql = "SELECT MAX(date) as date FROM vacancies WHERE source = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, source);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp temp = rs.getTimestamp("date");
                    if (temp != null) {
                        last = temp.toLocalDateTime();
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return last;
    }
}
