package example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;


/**
 * @autor aoliferov
 * @since 15.10.2018
 */
public class SQLStorage implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(SQLStorage.class);
    private Connection conn;

    public SQLStorage() throws IOException, SQLException, ClassNotFoundException {
        /* Данные для логина  */
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("chapter_013_SQL/src/main/resources/config.properties")) {
            properties.load(fis);
        }
        Class.forName("org.postgresql.Driver");
        /* Объект подключения */
        conn = DriverManager.getConnection(properties.getProperty("url"), properties);
    }

    @Override
    public void close() throws SQLException {
        conn.close();
        System.out.println("Ресурсы закрыты!");
    }

    /**
     * Пример запроса к БД c помошью JDBC
     */
    public void request() throws SQLException, IOException {

        /* Статический запрос */
        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM  users")
        ) {
            while (rs.next()) {
                System.out.println(String.format("%s %s", rs.getString("id"), rs.getString("name")));
            }
        }

        /* Динамический запрос */
        /* Реализация возвращения ключа добавленного элемента */
        String sql = "INSERT INTO users (name, role) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, String.format("User%s", new Random().nextInt(999)));
            ps.setInt(2, 1);
            ps.executeUpdate();
            try (ResultSet rs2 = ps.getGeneratedKeys()) {
                while (rs2.next()) {
                    System.out.println(String.format("Добавлен новый пользователь с индексом: %s", rs2.getInt(1)));
                }
            }
        }

        /* Выполнение запроса из файла */
        executeFile("query.sql");
    }

    /**
     * Пример метода для выполнения запроса из файла
     */
    public void executeFile(String nameFile) throws IOException, SQLException {
        try (FileInputStream fisSql = new FileInputStream(
                String.format("chapter_013_SQL/src/main/resources/%s", nameFile)
        )) {
            importSQL(conn, fisSql);
        }
    }

    /**
     * Метод выполнения запросов из InputStream
     */
    public void importSQL(Connection conn, InputStream in) throws SQLException {
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\\r)?\\n)|((\\r)?\\n)?(--)?.*(--(\\r)?\\n)");
        try (Statement st = conn.createStatement()) {
            while (s.hasNext()) {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }
                if (line.trim().length() > 0) {
                    st.execute(line);
                }
            }
        }
    }
}
