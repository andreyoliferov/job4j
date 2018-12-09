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
    }
}
