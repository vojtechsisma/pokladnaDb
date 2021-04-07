package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static Db instance;

    public static Db get() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new Db();
        }
        return instance;

    }

    private String cs = "jdbc:mysql://localhost:3306/pokladna";

    private Db() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(cs, "root", "");
    }
}
