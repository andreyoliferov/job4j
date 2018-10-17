package jdbc;

import java.io.IOException;
import java.sql.*;


/**
 * @autor aoliferov
 * @since 15.10.2018
 */
public class RunSQL {

    public static void main(String[] args) throws SQLException, IOException {
        /* try-with-resources */
        try (SQLStorage storage = new SQLStorage()) {
            storage.request();
        }
    }
}
