package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;

public class Db {
    private static Db instance;

    public static Db get() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new Db();
        }
        return instance;

    }

    private String cs = "jdbc:mysql://localhost:3306/pokladna", user = "root", pass = "";

    private Db() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    private final String typDB = "mysql";
    private final String serverName = "localhost";
    private final int port = 3306;
    private final String defaultDB = "pokladna";
    

    public Connection getConnection() throws SQLException {
        return getConnection(typDB, serverName, port, defaultDB, "root", "");
    }

    public Connection getConnection(String typDB, String serverName, int port, String defaultDB, String user, String pass) throws SQLException {
        return DriverManager.getConnection(cs, user, pass);
    }

}
