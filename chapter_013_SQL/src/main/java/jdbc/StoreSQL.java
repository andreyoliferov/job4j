package jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @autor aoliferov
 * @since 22.10.2018
 */
public class StoreSQL {

    private Connection conn;

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
     * Создание таблицы
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

    private void generate(int n) throws SQLException {
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

    private void print() throws SQLException {
        try (Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM entry")) {
            while (rs.next()) {
                System.out.println(rs.getInt("field"));
            }
        }
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        StoreSQL st = new StoreSQL();
        st.generate(20);
        st.print();
    }
}
