package ru.oliferov.storage.other;

import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 12.03.2019
 */
@Component("jdbcStorage")
@Scope("singleton")
public class JDBCStorage implements Storage {

    private Connection conn;

    public JDBCStorage(Environment env) throws SQLException, ClassNotFoundException {
        Class.forName(env.getProperty("driver-class-name"));
        conn = DriverManager.getConnection(
                env.getProperty("url").concat(env.getProperty("namedb")),
                env.getProperty("user"),
                env.getProperty("password")
        );
        conn.setAutoCommit(false);
    }

    @Override
    public UUID add(User user) {
        String sql = "INSERT INTO users (id, name, login) VALUES (?, ?, ?)";
        UUID id = UUID.randomUUID();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.setString(2, user.getName());
            ps.setString(3, user.getLogin());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            id = null;
        }
        return id;
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
