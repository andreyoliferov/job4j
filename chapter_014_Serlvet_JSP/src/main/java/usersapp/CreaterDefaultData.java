package usersapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 09.12.2018
 */
public class CreaterDefaultData {

    public void prepareData(Connection conn) throws SQLException {
        createTables(conn);
        createDefaultDataAccess(conn);
        createDefaultUsers(conn);
        createDefaultAddresses(conn);
        createDefaultSex(conn);
    }

    /**
     * Создание таблиц поумолчанию
     */
    private void createTables(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS roles (")
                            .append("id uuid primary key,")
                            .append("name varchar(300) not null unique)")
                            .toString()
            );
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS rules (")
                            .append("id uuid primary key,")
                            .append("name varchar(300) not null unique)")
                            .toString()
            );
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS roles_rules (")
                            .append("role uuid references roles(id),")
                            .append("rule uuid references rules(id),")
                            .append("constraint un unique(role, rule))")
                            .toString()
            );
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS access (")
                            .append("id uuid primary key,")
                            .append("path varchar(300),")
                            .append("rule uuid references rules(id),")
                            .append("constraint un_access unique(path, rule))")
                            .toString()
            );
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS countries (")
                            .append("id uuid primary key,")
                            .append("name varchar(300) unique)")
                            .toString()
            );
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS cities (")
                            .append("id uuid primary key,")
                            .append("name varchar(300) unique,")
                            .append("country uuid references countries(id))")
                            .toString()
            );
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS addresses (")
                            .append("id uuid primary key,")
                            .append("country uuid references countries(id),")
                            .append("city uuid references cities(id),")
                            .append("data varchar(500),")
                            .append("constraint ad_unique unique(country, city, data))")
                            .toString()
            );
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS users (")
                            .append("id uuid primary key,")
                            .append("name varchar(300),")
                            .append("login varchar(300) not null unique,")
                            .append("password varchar(300),")
                            .append("email varchar(300),")
                            .append("role uuid references roles(id),")
                            .append("date_created timestamp)")
                            .toString()
            );
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS sex_catalog (")
                            .append("id uuid primary key,")
                            .append("name varchar(100) not null unique)")
                            .toString()
            );
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS clients (")
                            .append("id uuid primary key,")
                            .append("first_name varchar(300),")
                            .append("second_name varchar(300),")
                            .append("phone varchar(100),")
                            .append("email varchar(300),")
                            .append("sex varchar(100) references sex_catalog(name),")
                            .append("address uuid references addresses(id),")
                            .append("owner uuid references users(id),")
                            .append("date_created timestamp)")
                            .toString()
            );
        }
    }


    /**
     * Создание данных доступа по умолчанию
     */
    private void createDefaultDataAccess(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO roles (id, name) VALUES(?,'administrator') ON CONFLICT DO NOTHING"
        )) {
            ps.setObject(1, UUID.randomUUID());
            ps.execute();
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO roles (id, name) VALUES(?,'guest') ON CONFLICT DO NOTHING"
        )) {
            ps.setObject(1, UUID.randomUUID());
            ps.execute();
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO rules (id, name) VALUES(?,?) ON CONFLICT DO NOTHING"
        )) {
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "view");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "create");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "update");
            ps.execute();
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO access (id, path, rule) VALUES(?, ?, (SELECT id FROM rules WHERE name = ?)) "
                        + "ON CONFLICT DO NOTHING"
        )) {
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "/list");
            ps.setObject(3, "view");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "/create");
            ps.setObject(3, "view");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "/create");
            ps.setObject(3, "create");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "/edit");
            ps.setObject(3, "view");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "/edit");
            ps.setObject(3, "update");
            ps.execute();
        }
        try (Statement st = conn.createStatement()) {
            st.execute("INSERT INTO roles_rules (role, rule) "
                    + "VALUES((SELECT id FROM roles WHERE name='administrator'), "
                    + "(SELECT id FROM rules WHERE name='view')) ON CONFLICT DO NOTHING");
            st.execute("INSERT INTO roles_rules (role, rule) "
                    + "VALUES((SELECT id FROM roles WHERE name='administrator'), "
                    + "(SELECT id FROM rules WHERE name='create')) ON CONFLICT DO NOTHING");
            st.execute("INSERT INTO roles_rules (role, rule) "
                    + "VALUES((SELECT id FROM roles WHERE name='administrator'), "
                    + "(SELECT id FROM rules WHERE name='update')) ON CONFLICT DO NOTHING");
            st.execute("INSERT INTO roles_rules (role, rule) "
                    + "VALUES((SELECT id FROM roles WHERE name='guest'), "
                    + "(SELECT id FROM rules WHERE name='view')) ON CONFLICT DO NOTHING");
        }
    }

    /**
     * Создание пользоветелей поумолчанию
     */
    private void createDefaultUsers(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (id, name, login, password, email, role, date_created) "
                        + "VALUES(?,'administrator', 'administrator', 'qwerty', 'administrator@administrator.ru', "
                        + "(SELECT id FROM roles WHERE name='administrator'), now()) "
                        + "ON CONFLICT DO NOTHING"
        )) {
            ps.setObject(1, UUID.randomUUID());
            ps.execute();
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (id, name, login, password, email, role, date_created) "
                        + "VALUES(?,'user', 'user', 'user', 'user@user.ru', "
                        + "(SELECT id FROM roles WHERE name='guest'), now()) "
                        + "ON CONFLICT DO NOTHING"
        )) {
            ps.setObject(1, UUID.randomUUID());
            ps.execute();
        }
    }

    /**
     * Создание стран и городов поумолчанию
     */
    private void createDefaultAddresses(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO countries (id, name) VALUES(?,?) ON CONFLICT DO NOTHING"
        )) {
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Россия");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Казахстан");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Украина");
            ps.execute();
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO cities (id, name, country) VALUES(?, ?, (SELECT id FROM countries WHERE name = ?)) "
                        + "ON CONFLICT DO NOTHING"
        )) {
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Омск");
            ps.setObject(3, "Россия");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Москва");
            ps.setObject(3, "Россия");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Санкт-Петербург");
            ps.setObject(3, "Россия");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Астана");
            ps.setObject(3, "Казахстан");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Алма-Ата");
            ps.setObject(3, "Казахстан");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Павлодар");
            ps.setObject(3, "Казахстан");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Киев");
            ps.setObject(3, "Украина");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Одесса");
            ps.setObject(3, "Украина");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "Львов");
            ps.setObject(3, "Украина");
            ps.execute();
        }
    }

    /**
     * Создание справочника полов по умолчанию
     */
    private void createDefaultSex(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO sex_catalog (id, name) VALUES(?,?) ON CONFLICT DO NOTHING"
        )) {
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "male");
            ps.execute();
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, "female");
            ps.execute();
        }
    }
}
