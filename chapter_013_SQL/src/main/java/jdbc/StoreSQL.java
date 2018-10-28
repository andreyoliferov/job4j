package jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @autor aoliferov
 * @since 22.10.2018
 */
public class StoreSQL {

    /**
     * Поле - объект подключения
     */
    private Connection conn;

    /**
     * Конструктор - создает подключение к БД. Создает таблицу, если она отсутствует.
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public StoreSQL() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("chapter_013_SQL/src/main/resources/storeSqlConfig.properties")) {
            properties.load(fis);
        }
        Class.forName(properties.getProperty("driver-class-name"));
        conn = DriverManager.getConnection(properties.getProperty("url"));
        prepareTable();
    }

    /**
     * Процедура создания таблицы
     */
    private void prepareTable() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute(
                    new StringBuilder()
                            .append("CREATE TABLE IF NOT EXISTS entry (")
                            .append("field integer)")
                            .toString()
            );
            st.execute(
                    "DELETE FROM entry"
            );
        }
    }

    /**
     * Процедура добавления данных в БД
     */
    public void generate(int n) throws SQLException {
        int i = 1;
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO entry (field) VALUES (?)")) {
            while (i <= n) {
                ps.setInt(1, i);
                ps.addBatch();
                i++;
            }
            ps.executeBatch();
        }
    }

    /**
     * Функция получения данных из таблицы
     * @return List данных
     */
    public List<StoreXML.Entry> getData() throws SQLException {
        List<StoreXML.Entry> data = new ArrayList<>();
        try (Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM entry")) {
            while (rs.next()) {
                data.add(new StoreXML.Entry(rs.getInt("field")));
            }
        }
        return data;
    }
}
