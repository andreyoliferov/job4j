package jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;


/**
 * @autor aoliferov
 * @since 15.10.2018
 */
public class SQLStorage implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(SQLStorage.class);
    private Connection conn;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    private ResultSet rs_2;

    @Override
    public void close() throws SQLException {
        conn.close();
        st.close();
        rs.close();
        ps.close();
        rs_2.close();
        System.out.println("Ресурсы закрыты!");
    }

    public void request() throws SQLException, IOException {

        /* Данные для логина  */
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("chapter_013_SQL/src/main/java/jdbc/config.properties")) {
            properties.load(fis);
        }

        /* Объект подключения */
        conn = DriverManager.getConnection(properties.getProperty("url"), properties);

        /* Статический запрос */
        st = conn.createStatement();
        rs = st.executeQuery("SELECT * FROM  users");
        while (rs.next()) {
            System.out.println(String.format("%s %s", rs.getString("id"), rs.getString("name")));
        }

        /* Динамический запрос */
        /* Реализация возвращения ключа добавленного элемента */
        ps = conn.prepareStatement("INSERT INTO users (name, role) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.format("User%s", new Random().nextInt(999)));
        ps.setInt(2, 1);
        ps.executeUpdate();
        rs_2 = ps.getGeneratedKeys();
        while (rs_2.next()) {
            System.out.println(rs_2.getInt(1));
        }
    }
}
